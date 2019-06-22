package hanwool.lotto.view.fragments.PastResult

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hanwool.lotto.R
import hanwool.lotto.database.Lotto
import hanwool.lotto.databinding.FragmentPastResultBinding
import hanwool.lotto.view.base.NavFragmentBase
import hanwool.lotto.view.fragments.PastResult.adapter.PastResultAdapter
import io.realm.Realm
import io.realm.Sort

class PastResult: NavFragmentBase<FragmentPastResultBinding>() {
    var realm: Realm? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_past_result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.recyclerView.adapter = PastResultAdapter(realm!!.where(Lotto::class.java).sort("drawNo", Sort.DESCENDING).findAll())
        mBinding!!.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
    }

    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }
}