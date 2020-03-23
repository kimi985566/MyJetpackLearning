package cn.yangchengyu.libnetwork

import java.lang.reflect.Type

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/23
 */
interface Convert<T> {
    fun convert(response: String?, type: Type?): T
}