package cn.yangchengyu.libcommon.ui

import androidx.paging.PageKeyedDataSource

class MutablePageKeyedDataSource<Value> : PageKeyedDataSource<Int, Value>() {

    var data: MutableList<Value> = mutableListOf()

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