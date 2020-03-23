package cn.yangchengyu.libnetwork

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/23
 */
internal object UrlCreator {

    @JvmStatic
    fun createUrlFromParams(url: String?, params: Map<String?, Any?>): String {
        if (url.isNullOrEmpty()) {
            return ""
        }

        val builder = StringBuilder(url)

        if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
            builder.append("&")
        } else {
            builder.append("?")
        }

        params.forEach { (key, data) ->
            try {
                if (!key.isNullOrEmpty() && data != null) {
                    val value = URLEncoder.encode(data.toString(), "UTF-8")
                    builder.append(key).append("=").append(value).append("&")
                }
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }

        return builder.deleteCharAt(builder.length - 1).toString()
    }
}