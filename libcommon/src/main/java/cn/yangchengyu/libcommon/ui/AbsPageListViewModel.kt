package cn.yangchengyu.libcommon.ui

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.BoundaryCallback
import cn.yangchengyu.libcommon.model.NetworkState
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Desc  : 基类的ViewModel
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

abstract class AbsPageListViewModel<T> : ViewModel(), LifecycleObserver {

    //dataSource
    var dataSource: DataSource<Int, T>? = null

    //PageData
    var pagedListData: LiveData<PagedList<T>>? = null

    //网络状态
    val networkState by lazy { MutableLiveData<NetworkState>() }

    //boundaryPageData
    val boundaryPageData by lazy { MutableLiveData<Boolean>() }

    //PageList Config
    protected val config: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(12)
            .build()
    }

    /**
     * PagedList数据被加载 情况的边界回调callback
     * 但 不是每一次分页 都会回调这里，具体请看 ContiguousPagedList#mReceiver#onPageResult
     * deferBoundaryCallbacks
     * */
    private var callback: BoundaryCallback<T> = object : BoundaryCallback<T>() {
        override fun onZeroItemsLoaded() {
            //新提交的PagedList中没有数据
            boundaryPageData.postValue(false)
        }

        override fun onItemAtFrontLoaded(itemAtFront: T) {
            //新提交的PagedList中第一条数据被加载到列表上
            boundaryPageData.postValue(true)
        }

        override fun onItemAtEndLoaded(itemAtEnd: T) {
            //新提交的PagedList中最后一条数据被加载到列表上
        }
    }

    /**
     * Datasource Factory
     * */
    private var factory: DataSource.Factory<Int, T> = object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            dataSource?.takeIf { dataSource ->
                dataSource.isInvalid
            }?.let { dataSource ->
                return dataSource
            } ?: run {
                return createDataSource()
            }
        }
    }

    init {
        pagedListData = LivePagedListBuilder(factory, config)
            .setInitialLoadKey(0)
            .setBoundaryCallback(callback)
            .build()
    }

    fun viewModelLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (exception: Exception) {
                LogUtils.e(this.javaClass.simpleName + " " + exception.toString())
            }
        }
    }

    //创建DataSource
    abstract fun createDataSource(): DataSource<Int, T>

    //可以在这个方法里 做一些清理 的工作
    override fun onCleared() {

    }
}