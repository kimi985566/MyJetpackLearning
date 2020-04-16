package cn.yangchengyu.libcommon.ui

import androidx.paging.ItemKeyedDataSource

abstract class MutableItemKeyedDataSource<Key, Value> : ItemKeyedDataSource<Key, Value>() {

    var data: MutableList<Value> = mutableListOf()

    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<Value>) {
        callback.onResult(data)
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Value>) {

    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Value>) {
        callback.onResult(emptyList())
    }

    abstract override fun getKey(item: Value): Key

}