package hanwool.lotto.view.fragments.MostAppeardBall.adapter

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.keyIterator
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hanwool.lotto.R
import hanwool.lotto.database.Lotto
import hanwool.lotto.databinding.ItemMostAppearedBallBinding
import io.realm.RealmResults

data class MostAppearedBallItem(
    val mBinding: ItemMostAppearedBallBinding
): RecyclerView.ViewHolder(mBinding.root)

class MostAppearedBallAdapter(lottoResults: RealmResults<Lotto>): RecyclerView.Adapter<MostAppearedBallItem>() {

    data class LottoBalls(
        val ballNum: Int,
        val ballCount: Int
    )
    val lottoList = ArrayList<LottoBalls>(46)

    init {
        val lottoMap = SparseIntArray()
        for (lotto in lottoResults) {
            lottoMap.put(lotto.ball1, lottoMap.get(lotto.ball1, 0) + 1)
            lottoMap.put(lotto.ball2, lottoMap.get(lotto.ball2, 0) + 1)
            lottoMap.put(lotto.ball3, lottoMap.get(lotto.ball3, 0) + 1)
            lottoMap.put(lotto.ball4, lottoMap.get(lotto.ball4, 0) + 1)
            lottoMap.put(lotto.ball5, lottoMap.get(lotto.ball5, 0) + 1)
            lottoMap.put(lotto.ball6, lottoMap.get(lotto.ball6, 0) + 1)
            lottoMap.put(lotto.ballBonus, lottoMap.get(lotto.ballBonus, 0) + 1)
        }
        for (ball in lottoMap.keyIterator()) {
            lottoList.add(LottoBalls(ball, lottoMap.get(ball, 0)))
        }
        lottoList.sortByDescending { lottoBallSortSelector(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostAppearedBallItem {
        val mBinding = DataBindingUtil.inflate<ItemMostAppearedBallBinding>(LayoutInflater.from(parent.context), R.layout.item_most_appeared_ball, parent, false)
        return MostAppearedBallItem(mBinding)
    }

    override fun getItemCount(): Int {
        return lottoList.size
    }

    override fun onBindViewHolder(holder: MostAppearedBallItem, position: Int) {
        holder.mBinding.ballNumber = lottoList[position].ballNum
        holder.mBinding.ballCount = lottoList[position].ballCount
    }

    companion object{
        fun lottoBallSortSelector(ball: LottoBalls): Int {
            return ball.ballCount
        }
    }
}