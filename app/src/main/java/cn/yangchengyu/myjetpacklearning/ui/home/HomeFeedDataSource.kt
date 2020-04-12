package cn.yangchengyu.myjetpacklearning.ui.home

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.myjetpacklearning.ext.executeResponse
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

/**
 * Desc  : 首页DataSource
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

class HomeFeedDataSource(private val scope: CoroutineScope, private val feedType: String?) :
    ItemKeyedDataSource<Int, Feed>() {

    private val repository by lazy { HomeRepository() }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Feed?>) {
        //加载初始化数据的
        Log.e(this.javaClass.simpleName, "loadInitial: ")
        loadData(0, params.requestedLoadSize, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Feed?>) {
        //向后加载分页数据的
        Log.i(this.javaClass.simpleName, "loadAfter: ")
        loadData(params.key, params.requestedLoadSize, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Feed?>) {
        //能够向前加载数据的
        callback.onResult(Collections.emptyList())
    }

    override fun getKey(item: Feed): Int = item.id

    override fun invalidate() {
        super.invalidate()

        scope.cancel()
    }

    private fun loadData(key: Int, count: Int, callback: LoadCallback<Feed?>) {
        scope.launch {
            try {
                val homeListResponse = repository.getHomeList(feedType, "", key, count)

                executeResponse(
                    response = homeListResponse,
                    successBlock = {
                        homeListResponse.body?.filterNotNull()?.let { list ->
                            callback.onResult(list)
                        }
                    },
                    errorBlock = {
                        callback.onResult(Collections.emptyList())
                    }
                )
            } catch (exception: Exception) {
                LogUtils.e(this.javaClass.simpleName + " " + exception.toString())
            }
        }
    }
}