package cn.yangchengyu.myjetpacklearning.ui.home.repository

import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libcommon.ui.MutableItemKeyedDataSource

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/16
 */

class HomeFeedItemKeyedDataSource : MutableItemKeyedDataSource<Int, Feed>() {
    override fun getKey(item: Feed): Int = item.id
}