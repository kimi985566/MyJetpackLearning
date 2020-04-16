package cn.yangchengyu.myjetpacklearning.ui.home

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import cn.yangchengyu.libnetwork.RetrofitFactory
import java.util.concurrent.Executors

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */
object HomeFeedInjection {

    private fun provideCache(context: Context): HomeFeedLocalCache {
        return HomeFeedLocalCache(Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): HomeRepository {
        return HomeRepository(RetrofitFactory.homeService,provideCache(context))
    }

    fun provideHomeViewModelFactory(context: Context): ViewModelProvider.Factory {
        return HomeViewModelFactory(provideGithubRepository(context))
    }
}