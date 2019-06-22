package hanwool.lotto.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import hanwool.lotto.R
import hanwool.lotto.data.ApiService
import hanwool.lotto.data.model.LottoDto
import hanwool.lotto.database.Lotto
import hanwool.lotto.databinding.ActivityDeepLinkBinding
import hanwool.lotto.view.base.ActicityBase
import hanwool.lotto.view.base.NavFragmentBase
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.net.SocketTimeoutException

class DeepLinkActivity: ActicityBase<ActivityDeepLinkBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_deep_link
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, SplashActicity::class.java)
        val url = getIntent().data.toString()
        var drawNo = url.replace(DEEP_LINK_BASE_URL, "")
        if(drawNo.isEmpty()) {
            Toast.makeText(this, R.string.error_no_draw_no, Toast.LENGTH_LONG).show()
            finish()
        } else {
            if(drawNo.startsWith("/")) {
                drawNo = drawNo.replace("/", "")
            }
            val drawNoInt = drawNo.toInt(10)
            title = String.format(getString(R.string.title_deep_link), drawNoInt)

            GlobalScope.launch(Dispatchers.IO) {
                launch(Dispatchers.IO) {
                    try {
                        val call = ApiService.lottoApiCall().getLottoInfo(drawNoInt)
                        val lottoData = call.execute().body()!!
                        if (lottoData.result == "success") {
                            mBinding!!.lottery = lottoData
                        } else {
                            Toast.makeText(applicationContext, R.string.error_over_draw_no, Toast.LENGTH_LONG).show()
                        }
                    } catch (e: SocketTimeoutException) {
                        Log.e(TAG, e.message, e)
                    } catch (e: NumberFormatException) {
                        Toast.makeText(applicationContext, R.string.error_no_draw_no, Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }
    }

    companion object{
        val TAG = DeepLinkActivity::class.java.simpleName
        val DEEP_LINK_BASE_URL = "app://hanwool.lotto"
    }
}