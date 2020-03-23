package cn.yangchengyu.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/23
 */
class JsonConvert : Convert<Any?> {

    /**
     * 默认的Json转 Java Bean的转换器
     * */
    override fun convert(response: String?, type: Type?): Any? {
        val jsonData = JSON.parseObject(response)?.getJSONObject("data")?.get("data")?.toString()
            ?: return null

        return JSON.parseObject(jsonData, type)
    }
}