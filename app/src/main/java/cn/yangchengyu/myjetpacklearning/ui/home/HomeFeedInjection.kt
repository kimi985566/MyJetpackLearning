package cn.yangchengyu.myjetpacklearning.ui.home

import androidx.lifecycle.ViewModelProvider
import cn.yangchengyu.libnetwork.RetrofitFactory
import cn.yangchengyu.myjetpacklearning.ui.home.repository.HomeFeedLocalCache
import cn.yangchengyu.myjetpacklearning.ui.home.repository.HomeRepository
import cn.yangchengyu.myjetpacklearning.ui.home.viewmodel.HomeViewModelFactory

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */
object HomeFeedInjection {

    private fun provideCache(): HomeFeedLocalCache {
        return HomeFeedLocalCache()
    }

    private fun provideHomeRepository(): HomeRepository {
        return HomeRepository(
            RetrofitFactory.homeService,
            provideCache()
        )
    }

    fun provideHomeViewModelFactory(): ViewModelProvider.Factory {
        return HomeViewModelFactory(
            provideHomeRepository()
        )
    }
}