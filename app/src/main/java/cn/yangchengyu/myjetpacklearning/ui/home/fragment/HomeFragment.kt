package cn.yangchengyu.myjetpacklearning.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.yangchengyu.libcommon.model.NetworkState
import cn.yangchengyu.libnavannotation.FragmentDestination
import cn.yangchengyu.myjetpacklearning.R
import cn.yangchengyu.myjetpacklearning.ui.home.HomeFeedInjection
import cn.yangchengyu.myjetpacklearning.ui.home.adapter.HomeFeedAdapter
import cn.yangchengyu.myjetpacklearning.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var adapter: HomeFeedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel =
            ViewModelProviders.of(this, HomeFeedInjection.provideHomeViewModelFactory())
                .get(HomeViewModel::class.java)

        initAdapter()

        homeViewModel.repos.observe(viewLifecycleOwner, Observer {
            adapter?.submitList(it)
        })

        homeViewModel.networkState.observe(viewLifecycleOwner, Observer { state ->
            swipeRefresh.isRefreshing = NetworkState.LOADING == state
        })

        swipeRefresh?.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh?.setOnRefreshListener {
            homeViewModel.refresh()
        }

        homeViewModel.getFeedType("all")
    }

    private fun initAdapter() {
        adapter = HomeFeedAdapter(context!!)
        list.adapter = adapter
    }
}
