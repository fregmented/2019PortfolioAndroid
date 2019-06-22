package hanwool.lotto.view.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hanwool.lotto.R
import hanwool.lotto.data.ApiService
import hanwool.lotto.data.event.LottoDataEvent
import hanwool.lotto.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActicity : AppCompatActivity() {

    val eventBus = LottoDataEvent()
    val maxDrawNo = Utils.getLottoDrawNo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        eventBus.toObserverable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(it.result == "success") {
                    number_progress_bar.progress = it.drawNo
                    txt_current_draw.text = it.drawNo.toString(10)
                } else {
                    val pref = getSharedPreferences(getString(R.string.conf_main), Context.MODE_PRIVATE)
                    if (pref.getBoolean(getString(R.string.conf_is_seen_welcome), false)) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, WelcomeActivity::class.java))
                    }
                    finish()
                }
            }

        number_progress_bar.max = maxDrawNo
        txt_max_draw.text = maxDrawNo.toString(10)
        ApiService.retriveAllLottoData(eventBus, maxDrawNo)
    }
}
