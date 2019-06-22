package hanwool.lotto.util

import android.content.Context
import android.util.Log
import java.util.*

object Utils {
    fun getLottoDrawNo(): Int {
        val lottoStartDate = Calendar.getInstance()
        lottoStartDate.set(2002, Calendar.DECEMBER,7)
        val currentDate = Calendar.getInstance()
        currentDate.time = Date()
        val currentDrawNo = ((currentDate.timeInMillis - lottoStartDate.timeInMillis)/ 1000 / (24*60*60) / 7) + 1
        Log.i("getLottoDrawNo", "currentDrawNo: $currentDrawNo")
        return currentDrawNo.toInt()
    }
}