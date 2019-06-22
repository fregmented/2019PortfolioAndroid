package hanwool.lotto.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hanwool.lotto.R
import hanwool.lotto.databinding.ActivityMainBinding
import hanwool.lotto.view.base.ActicityBase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: ActicityBase<ActivityMainBinding>() {

    var navigation: NavController? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        navigation = Navigation.findNavController(this, nav_host_fragment.id)
        NavigationUI.setupActionBarWithNavController(this, navigation!!)
        toolbar.setNavigationOnClickListener {
            if(navigation!!.currentDestination?.id != R.id.main_fragment) {
                navigation!!.navigateUp()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.i(TAG, "navigation!!.currentDestination?.id: ${navigation!!.currentDestination?.id} R.id.main_fragment: ${R.id.main_fragment}")
        if(navigation!!.currentDestination?.id != R.id.main_fragment) {
            finish()
        } else {
            navigation!!.navigateUp()
        }
    }

    companion object{
        val TAG = MainActivity::class.java.simpleName
    }
}