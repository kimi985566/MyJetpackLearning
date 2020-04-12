package cn.yangchengyu.myjetpacklearning.ui.home

import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libnetwork.RetrofitFactory
import cn.yangchengyu.libnetwork.model.ApiResponse
import cn.yangchengyu.libnetwork.repository.BaseRepository

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

class HomeRepository : BaseRepository() {
    /**
     * 获取首页数据
     * */
    suspend fun getHomeList(
        feedType: String?,
        userId: String?,
        feedId: Int?,
        pageCount: Int?
    ): ApiResponse<List<Feed?>?> {
        return apiCall {
            RetrofitFactory.homeService.getHomeFeedList(
                feedType ?: "",
                userId ?: "",
                feedId ?: 0,
                pageCount ?: 0
            )
        }
    }
}