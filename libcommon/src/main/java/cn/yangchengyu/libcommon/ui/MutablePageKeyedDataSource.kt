package cn.yangchengyu.libcommon.ui

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList

class MutablePageKeyedDataSource<Value> : PageKeyedDataSource<Int, Value>() {

    var data: MutableList<Value> = mutableListOf()

    fun buildNewPagedList(config: PagedList.Config): PagedList<Value> =
        PagedList.Builder(this, config).build()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Value>
    ) {
        callback.onResult(data, null, null)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        callback.onResult(emptyList(), null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        callback.onResult(emptyList(), null)
    }
}