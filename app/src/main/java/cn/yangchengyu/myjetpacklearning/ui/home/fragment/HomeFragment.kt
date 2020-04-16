package cn.yangchengyu.myjetpacklearning.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.yangchengyu.libnavannotation.FragmentDestination
import cn.yangchengyu.myjetpacklearning.R
import cn.yangchengyu.myjetpacklearning.ui.home.adapter.HomeFeedAdapter
import cn.yangchengyu.myjetpacklearning.ui.home.HomeFeedInjection
import cn.yangchengyu.myjetpacklearning.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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
            ViewModelProviders.of(this,
                HomeFeedInjection.provideHomeViewModelFactory(
                    context!!
                )
            )
                .get(HomeViewModel::class.java)

        initAdapter()

        homeViewModel.refreshFeedType("all")
    }

    private fun initAdapter() {
        list.adapter =
            HomeFeedAdapter(
                context!!
            )
        homeViewModel.repos.observe(viewLifecycleOwner, Observer {
            (list.adapter as? HomeFeedAdapter)?.submitList(it)
        })
        homeViewModel.networkErrors.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }
}
