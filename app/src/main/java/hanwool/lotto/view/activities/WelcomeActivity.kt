package hanwool.lotto.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import hanwool.lotto.R
import hanwool.lotto.databinding.ActivityWelcomeBinding
import hanwool.lotto.view.base.ActicityBase
import kotlinx.android.synthetic.main.activity_welcome.*



class WelcomeActivity: ActicityBase<ActivityWelcomeBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    fun onViewClick(view: View) {
        Log.i(TAG, "onViewClick:" + view.id)
        if (view.id == btn_start.id) {
            val pref = this.getSharedPreferences(getString(R.string.conf_main), Context.MODE_PRIVATE)
            pref.edit().putBoolean(getString(R.string.conf_is_seen_welcome), true).apply()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding!!.fragment = this
    }

    companion object{
        val TAG = WelcomeActivity::class.java.simpleName
    }
}