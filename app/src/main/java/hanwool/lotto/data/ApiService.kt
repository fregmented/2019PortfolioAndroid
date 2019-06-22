package hanwool.lotto.data

import android.annotation.SuppressLint
import android.util.Log
import hanwool.lotto.data.event.LottoDataEvent
import hanwool.lotto.data.model.LottoDto
import hanwool.lotto.database.Lotto
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.net.SocketTimeoutException
import kotlin.math.max

object ApiService {
    private val TAG = ApiService.javaClass.simpleName

    fun lottoApiCall() = Retrofit.Builder()
        .baseUrl("https://www.dhlottery.co.kr/")
        .addConverterFactory(ApiWorker.gsonConverter)
        .client(ApiWorker.client)
        .build()
        .create(LottoApi::class.java)


    @SuppressLint("CheckResult")
    @Throws(Throwable::class)
    fun retriveAllLottoData(eventBus: LottoDataEvent, maxDrawNo: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.IO) {
                val realm = Realm.getDefaultInstance()
                var currentLastDrawNo = realm.where(Lotto::class.java).count() + 1
                var drawNumberRange = currentLastDrawNo..maxDrawNo
                for (drawNumber in drawNumberRange) {
                    try {
                        getLotto(drawNumber.toInt(), realm, eventBus)
                    } catch (e: SocketTimeoutException) {
                        Log.e(TAG, e.message, e)
                    }
                }
            }.join()
            eventBus.send(LottoDto(result="end"))
        }
    }

    fun getLotto(drawNumber: Int, realm: Realm, eventBus: LottoDataEvent) {
        val call = ApiService.lottoApiCall().getLottoInfo(drawNumber)
        val lottoData = call.execute().body()!!
        if (lottoData.result == "success") {
            try {
                realm.executeTransaction {
                    it.copyToRealm(
                        Lotto(
                            lottoData.drawNo, lottoData.ball1, lottoData.ball2,
                            lottoData.ball3, lottoData.ball4, lottoData.ball5, lottoData.ball6,
                            lottoData.ballBonus
                        )
                    )
                }
            } catch (e: Throwable) {
                Log.e(TAG, e.message, e)
            }
        }
        eventBus.send(lottoData)
    }
}

interface LottoApi {
    @Headers("Accept: application/json")
    @GET("common.do")
    fun getLottoInfo(
        @Query("drwNo") drawNo: Int
    ): Call<LottoDto>
}