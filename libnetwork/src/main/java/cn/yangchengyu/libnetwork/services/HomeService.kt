package cn.yangchengyu.libnetwork.services

import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libnetwork.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

interface HomeService {
    /**
     * 获取首页列表
     * */
    @GET("/feeds/queryHotFeedsList")
    suspend fun getHomeFeedList(
        @Query("feedType") feedType: String,
        @Query("userId") userId: String,
        @Query("feedId") key: Int,
        @Query("pageCount") count: Int
    ): ApiResponse<List<Feed?>?>
}