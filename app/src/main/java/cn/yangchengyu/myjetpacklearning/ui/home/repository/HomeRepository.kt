package cn.yangchengyu.myjetpacklearning.ui.home.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libnetwork.repository.BaseRepository
import cn.yangchengyu.libnetwork.services.HomeService
import cn.yangchengyu.myjetpacklearning.ext.executeResponse
import cn.yangchengyu.myjetpacklearning.ext.tryCatchLaunch
import com.blankj.utilcode.util.LogUtils

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

class HomeRepository(
    private val service: HomeService,
    private val cache: HomeFeedLocalCache
) : BaseRepository() {

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
        private const val NETWORK_ITEM_SIZE = 20
    }

    fun refresh(feedType: String): HomeFeedResult {

        // Get data source factory from the local cache
        val dataSourceFactory = cache.getFeeds(DATABASE_PAGE_SIZE)

        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback = HomeFeedBoundaryCallback(feedType)
        val networkErrors = boundaryCallback.networkErrors

        val config = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE_SIZE)
            .build()

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return HomeFeedResult(
            data,
            networkErrors
        )
    }

    inner class HomeFeedBoundaryCallback(private val feedType: String) :
        PagedList.BoundaryCallback<Feed>() {

        // keep the last requested page. When the request is successful, increment the page number.
        private var lastKey = 0

        private val _networkErrors = MutableLiveData<String>()

        // LiveData of network errors.
        val networkErrors: LiveData<String>
            get() = _networkErrors

        // avoid triggering multiple requests in the same time
        private var isRequestInProgress = false

        /**
         * Database returned 0 items. We should query the backend for more items.
         */
        override fun onZeroItemsLoaded() {
            LogUtils.i("onZeroItemsLoaded")
            requestAndSaveData(feedType)
        }

        /**
         * When all items in the database were loaded, we need to query the backend for more items.
         */
        override fun onItemAtEndLoaded(itemAtEnd: Feed) {
            LogUtils.i("onItemAtEndLoaded")
            requestAndSaveData(feedType)
        }

        private fun requestAndSaveData(feedType: String) {
            if (isRequestInProgress) return

            isRequestInProgress = true

            tryCatchLaunch(
                tryBlock = {
                    executeResponse(
                        response = apiCall {
                            service.getHomeFeedList(
                                feedType,
                                "",
                                lastKey,
                                NETWORK_ITEM_SIZE
                            )
                        },
                        successBlock = { response ->
                            response.data?.data?.filterNotNull()?.let { list ->
                                cache.insertFeeds(list) {
                                    if (list.isNotEmpty()) {
                                        lastKey = list[list.size - 1].id
                                    }
                                    isRequestInProgress = false
                                }
                            }
                        },
                        errorBlock = { errorMsg ->
                            _networkErrors.postValue(errorMsg)
                            isRequestInProgress = false
                        }
                    )
                },
                catchBlock = {
                    _networkErrors.postValue("请求异常")
                    isRequestInProgress = false
                }
            )
        }
    }
}