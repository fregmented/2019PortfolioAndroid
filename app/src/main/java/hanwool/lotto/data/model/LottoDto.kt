package hanwool.lotto.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class LottoDto (
    @SerializedName("totSellamnt")
    val totalSellingLottoTicket: Long = 0,
    @SerializedName("returnValue")
    val result: String = "",
    @SerializedName("drwNoDate")
    val date: Date = Date(),
    @SerializedName("drwNo")
    val drawNo: Int = 0,
    @SerializedName("drwtNo1")
    val ball1: Int = 0,
    @SerializedName("drwtNo2")
    val ball2: Int = 0,
    @SerializedName("drwtNo3")
    val ball3: Int = 0,
    @SerializedName("drwtNo4")
    val ball4: Int = 0,
    @SerializedName("drwtNo5")
    val ball5: Int = 0,
    @SerializedName("drwtNo6")
    val ball6: Int = 0,
    @SerializedName("bnusNo")
    val ballBonus: Int = 0,

    @Expose(deserialize = false)
    @SerializedName("firstWinamnt")
    val prizeAmountForWinnerPerOnePerson: Long = 0,
    @Expose(deserialize = false)
    @SerializedName("firstPrzwnerCo")
    val winnerCount: Long = 0,
    @Expose(deserialize = false)
    @SerializedName("firstAccumamnt")
    val totalAccumulatedPrizeAmount: Long = 0
)