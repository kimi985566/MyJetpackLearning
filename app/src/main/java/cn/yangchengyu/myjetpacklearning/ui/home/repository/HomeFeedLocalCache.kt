package cn.yangchengyu.myjetpacklearning.ui.home.repository

import androidx.paging.DataSource
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libnetwork.cache.CacheDatabase
import java.util.concurrent.Executor

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */

class HomeFeedLocalCache(private val ioExecutor: Executor) {

    fun insertFeeds(data: List<Feed>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            //将response中的data以cache存储
            CacheDatabase.get().feed.insertFeeds(data)
            insertFinished()
        }
    }

    fun getFeeds(): DataSource.Factory<Int, Feed> {
        return CacheDatabase.get().feed.getFeeds()
    }
}