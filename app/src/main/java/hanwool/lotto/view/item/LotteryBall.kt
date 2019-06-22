package hanwool.lotto.view.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import hanwool.lotto.R
import kotlinx.android.synthetic.main.item_lottery_ball.view.*
import android.content.res.TypedArray
import android.view.Gravity
import android.widget.RelativeLayout


class LotteryBall @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
        RelativeLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.item_lottery_ball, this, true)
        gravity = Gravity.CENTER
        if (attrs != null) {
            getAttrs(attrs, defStyleAttr)
        }
    }

    private fun getAttrs(attrs: AttributeSet, defStyleAttr: Int = 0) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LotteryBall, defStyleAttr, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val number = typedArray.getInt(R.styleable.LotteryBall_number, 0)
        setNumber(number)
        typedArray.recycle()
    }

    fun setNumber(number: Int) {
        lottery_text.text = number.toString()
//        TODO: Color setting
        when (number) {
            in 1..10 -> {
                background = context.getDrawable(R.drawable.bg_lottery_ball_yellow)
            }
            in 11..20 -> {
                background = context.getDrawable(R.drawable.bg_lottery_ball_blue)
            }
            in 21..30 -> {
                background = context.getDrawable(R.drawable.bg_lottery_ball_red)
            }
            in 31..40 -> {
                background = context.getDrawable(R.drawable.bg_lottery_ball_grey)
            }
            in 41..45 -> {
                background = context.getDrawable(R.drawable.bg_lottery_ball_green)
            }
            else -> {
                background = context.getDrawable(R.drawable.bg_lottery_ball)
            }
        }
    }

    companion object{
        val TAG = LotteryBall::class.java.simpleName
    }
}