package cn.yangchengyu.myjetpacklearning.ui.home.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libcommon.model.HomeFeedData
import cn.yangchengyu.libcommon.model.NetworkState
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
        private const val NETWORK_ITEM_SIZE = 20
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastKey = 0

    fun refresh(feedType: String): LiveData<HomeFeedResult> = liveData {
        // Get data source factory from the local cache
        val dataSourceFactory = cache.getFeeds()

        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback = HomeFeedBoundaryCallback(feedType)
        val networkState = boundaryCallback.networkState

        val config = PagedList.Config.Builder()
            .setPageSize(NETWORK_ITEM_SIZE)
            .setInitialLoadSizeHint(NETWORK_ITEM_SIZE)
            .build()

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        emit(
            HomeFeedResult(
                data = data,
                networkState = networkState,
                refresh = {
                    cache.deleteAllFeeds {
                        //delete all data and get data from 0
                        lastKey = 0
                    }
                }
            )
        )
    }

    inner class HomeFeedBoundaryCallback(private val feedType: String) :
        PagedList.BoundaryCallback<Feed>() {

        private val _networkState = MutableLiveData<NetworkState>()

        val networkState: LiveData<NetworkState>
            get() = _networkState

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

        override fun onItemAtFrontLoaded(itemAtFront: Feed) {

        }

        private fun requestAndSaveData(feedType: String) {
            if (isRequestInProgress) return

            _networkState.postValue(NetworkState.LOADING)
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
                        successBlock = { data ->
                            processData(data)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        errorBlock = { errorMsg ->
                            isRequestInProgress = false
                            _networkState.postValue(NetworkState.error(errorMsg))
                        }
                    )
                },
                catchBlock = { exception ->
                    isRequestInProgress = false
                    _networkState.postValue(NetworkState.error(exception.message))
                }
            )
        }

        private suspend fun processData(data: HomeFeedData?) {
            data?.data?.filterNotNull()?.let { list ->
                cache.insertFeeds(list) {
                    if (list.isNotEmpty()) {
                        lastKey = list[list.size - 1].id
                    }
                    isRequestInProgress = false
                }
            }
        }
    }
}