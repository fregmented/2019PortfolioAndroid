package hanwool.lotto.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import hanwool.lotto.R
import hanwool.lotto.data.model.LottoDto
import hanwool.lotto.database.Lotto
import hanwool.lotto.databinding.FragmentMainBinding
import hanwool.lotto.view.base.NavFragmentBase
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.text.Editable
import android.widget.Toast
import hanwool.lotto.util.Utils


class MainFragment: NavFragmentBase<FragmentMainBinding>() {

    var realm: Realm? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    fun onViewClick(view: View) {
        when {
            view.id == btn_create_lottery.id -> {
                val rng = Random() // Ideally just create one instance globally
// Note: use LinkedHashSet to maintain insertion order
                val generated = LinkedHashSet<Int>()
                while (generated.size < 7) {
                    val next = rng.nextInt(45) + 1
                    // As we're adding to a set, this will automatically do a containment check
                    generated.add(next)
                }
                val balls = ArrayList<Int>(generated)
                val lottoDto = LottoDto(ball1 = balls[0], ball2 = balls[1], ball3 = balls[2], ball4 = balls[3],
                    ball5 = balls[4], ball6 = balls[5], ballBonus = balls[6])
                mBinding!!.createdLottery = lottoDto
            }
            view.id == btn_check_win.id -> {
                val selectedDrawNoStr = edit_draw_no.text.toString()
                if(selectedDrawNoStr.isNotEmpty() && mBinding!!.createdLottery != null) {
                    val selectedDrawNo = selectedDrawNoStr.toInt(10)
                    if(realm!!.where(Lotto::class.java).equalTo("drawNo", selectedDrawNo).count() > 0) {
                        val selectedDrawObj = realm!!.where(Lotto::class.java).equalTo("drawNo", selectedDrawNo).findFirst()
                        val selectedDrawList = listOf<Int>(selectedDrawObj!!.ball1, selectedDrawObj.ball2, selectedDrawObj.ball3,
                            selectedDrawObj.ball4, selectedDrawObj.ball5, selectedDrawObj.ball6)
                        val createdDrawList = listOf<Int>(mBinding!!.createdLottery!!.ball1, mBinding!!.createdLottery!!.ball2,
                            mBinding!!.createdLottery!!.ball3,mBinding!!.createdLottery!!.ball4, mBinding!!.createdLottery!!.ball5,
                            mBinding!!.createdLottery!!.ball6)
                        val drawSet = createdDrawList.intersect(selectedDrawList)
                        var winPlace = 0
//                            TODO: Dialog
                        when {
                            drawSet.count() == 3 -> { winPlace = 5 }
                            drawSet.count() == 4 -> { winPlace = 4 }
                            drawSet.count() == 5 -> {
                                if(selectedDrawObj.ballBonus == mBinding!!.createdLottery!!.ballBonus) {
                                    winPlace = 3
                                } else {
                                    winPlace = 2
                                }
                            }
                            drawSet.count() == 6 -> { winPlace = 1 }
                            else -> { winPlace = 6 }
                        }
                        var message = ""
                        if(winPlace == 6) {
                            message = context!!.getString(R.string.msg_prize_failed)
                        } else {
                            message = String.format(context!!.getString(R.string.msg_prize_win), winPlace)
                        }
                        AlertDialog.Builder(context!!)
                            .setMessage(message)
                            .setPositiveButton(R.string.btn_ok) {
                                    dialogInterface, _ -> dialogInterface.dismiss()
                            }
                            .create().show()
                        Log.i(TAG, "Lotto result: $message")
                    }
                } else {
                    if(mBinding!!.createdLottery == null) {
                        Snackbar.make(mBinding!!.root, R.string.error_no_lotto, Snackbar.LENGTH_SHORT).show()
                    }
                    if(selectedDrawNoStr.isEmpty()) {
                        Snackbar.make(mBinding!!.root, R.string.error_no_draw_no, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            view.id == btn_view_past_result.id -> {
                navi!!.navigate(R.id.action_main_fragment_to_past_result_fragment)
            }
            view.id == btn_view_most_appeared.id -> {
                navi!!.navigate(R.id.action_main_fragment_to_most_appeared_ball_fragment)
            }
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.fragment = this
        mBinding!!.editDrawNo.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.i(TAG, "beforeTextChanged s: $s, start: $start, count: $count, after: $after")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanged s: $s, start: $start, count: $count, after: $before, realmCount: ${realm!!.where(Lotto::class.java).count()}")
                if(s.isNotEmpty() && s.toString().toLong() > Utils.getLottoDrawNo()) {
                    Toast.makeText(context!!, R.string.error_over_draw_no, Toast.LENGTH_SHORT).show()
                    mBinding!!.editDrawNo.setText(realm!!.where(Lotto::class.java).count().toString())
                }
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }

    companion object{
        val TAG = MainFragment::class.java.simpleName
    }
}