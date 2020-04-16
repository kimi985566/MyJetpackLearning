package cn.yangchengyu.myjetpacklearning.ui.home

import androidx.paging.DataSource
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libcommon.model.HomeFeedData
import cn.yangchengyu.libcommon.ui.MutablePageKeyedDataSource
import cn.yangchengyu.libnetwork.cache.CacheManager
import java.util.concurrent.Executor

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */

class HomeFeedLocalCache(private val ioExecutor: Executor) {

    fun insertFeeds(key: String, data: HomeFeedData, insertFinished: () -> Unit) {
        ioExecutor.execute {
            CacheManager.save(key, data)
            insertFinished()
        }
    }

    fun getFeeds(key: String): DataSource.Factory<Int, Feed> {
        return object : DataSource.Factory<Int, Feed>() {
            override fun create(): DataSource<Int, Feed> {
                val dataSource = MutablePageKeyedDataSource<Feed>()
                (CacheManager.getCache(key) as? HomeFeedData)?.data?.filterNotNull()?.takeIf {
                    it.isNotEmpty()
                }?.let { list ->
                    dataSource.data.addAll(list)
                }
                return dataSource
            }
        }
    }
}