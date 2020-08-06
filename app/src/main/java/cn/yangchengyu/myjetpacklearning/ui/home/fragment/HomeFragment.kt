package cn.yangchengyu.myjetpacklearning.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

        //设置ViewModel
        initViewModel()
        //设置Adapter
        initAdapter()
        //设置列表
        initRecyclerView()
    }

    private fun initAdapter() {
        adapter = HomeFeedAdapter(context!!)
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProvider(this, HomeFeedInjection.provideHomeViewModelFactory())
            .get(HomeViewModel::class.java)

        //刷新数据
        homeViewModel.getFeedType("all")

        homeViewModel.repos.observe(viewLifecycleOwner, Observer {
            adapter?.submitList(it)
        })
        homeViewModel.networkState.observe(viewLifecycleOwner, Observer { state ->
            swipeRefresh?.isRefreshing = NetworkState.LOADING == state
        })
    }

    private fun initRecyclerView() {
        //设置Adapter
        list?.adapter = adapter
        //默认给列表中的Item 一个 10dp的ItemDecoration
        context?.let { ctx ->
            list?.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL).apply {
                ContextCompat.getDrawable(ctx, R.drawable.list_divider)?.let { drawable ->
                    setDrawable(drawable)
                }
            })
        }
        //设置刷新布局
        swipeRefresh?.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh?.setOnRefreshListener {
            homeViewModel.refresh()
        }
    }
}
