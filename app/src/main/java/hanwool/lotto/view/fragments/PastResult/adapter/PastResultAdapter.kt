package hanwool.lotto.view.fragments.PastResult.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hanwool.lotto.R
import hanwool.lotto.database.Lotto
import hanwool.lotto.databinding.ItemPastResultBinding
import io.realm.RealmResults

data class PastResultItem(
    val mBinding: ItemPastResultBinding
): RecyclerView.ViewHolder(mBinding.root)

class PastResultAdapter(lottoResults: RealmResults<Lotto>): RecyclerView.Adapter<PastResultItem>() {

    val results = lottoResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastResultItem {
        val mBinding = DataBindingUtil.inflate<ItemPastResultBinding>(LayoutInflater.from(parent.context), R.layout.item_past_result, parent, false)
        return PastResultItem(mBinding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: PastResultItem, position: Int) {
        holder.mBinding.lottery = results[position]
    }
}