package cn.yangchengyu.myjetpacklearning.ext

import android.view.View
import cn.yangchengyu.libnetwork.model.ApiResponse
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

/**
 * Desc  : 扩展方法
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */

/**
 *  扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
 * 扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/**
 * 处理数据
 * */
suspend fun <T> executeResponse(
    response: ApiResponse<T>,
    successBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Unit,
    errorBlock: suspend CoroutineScope.(errorMsg: String) -> Unit
) {
    coroutineScope {
        if (200 == response.status && response.data != null) {
            successBlock(response)
        } else {
            errorBlock(response.message ?: "")
        }
    }
}

/**
 * try catch的协程
 * */
fun tryCatchLaunch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.() -> Unit
) {
    runBlocking {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                LogUtils.e(this.coroutineContext.javaClass.simpleName + " " + e.toString())
                catchBlock()
            }
        }
    }
}