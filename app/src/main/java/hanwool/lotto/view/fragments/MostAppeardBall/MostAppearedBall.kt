package hanwool.lotto.view.fragments.MostAppeardBall

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hanwool.lotto.R
import hanwool.lotto.database.Lotto
import hanwool.lotto.databinding.FragmentMostAppearedBallBinding
import hanwool.lotto.view.base.NavFragmentBase
import hanwool.lotto.view.fragments.MostAppeardBall.adapter.MostAppearedBallAdapter
import io.realm.Realm
import org.jetbrains.annotations.NotNull

class MostAppearedBall: NavFragmentBase<FragmentMostAppearedBallBinding>() {

    var realm: Realm? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_most_appeared_ball
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.recyclerView.adapter = MostAppearedBallAdapter(realm!!.where(Lotto::class.java).findAll())
        mBinding!!.recyclerView.layoutManager = GridLayoutManager(context!!, 4)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }
}