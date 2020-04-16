package cn.yangchengyu.libnetwork.repository

import androidx.lifecycle.LifecycleObserver
import cn.yangchengyu.libnetwork.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Desc  : BaseRepository
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
open class BaseRepository : LifecycleObserver {
    suspend fun <T> apiCall(call: suspend () -> ApiResponse<T>): ApiResponse<T> =
        withContext(Dispatchers.IO) { call.invoke() }
}