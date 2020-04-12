package cn.yangchengyu.libnetwork

import cn.yangchengyu.libnetwork.model.ApiResponse

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/23
 */
abstract class JsonCallback<T> {
    fun onSuccess(response: ApiResponse<T>?) {}
    fun onError(response: ApiResponse<T>?) {}
    fun onCacheSuccess(response: ApiResponse<T>?) {}
}