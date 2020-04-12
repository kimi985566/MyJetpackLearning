package cn.yangchengyu.myjetpacklearning.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libcommon.ui.AbsPageListViewModel


class HomeViewModel : AbsPageListViewModel<Feed>() {

    var feedType: String? = null

    override fun createDataSource(): DataSource<Int, Feed> {
        return HomeFeedDataSource(viewModelScope, feedType)
    }
}