package cn.yangchengyu.libnetwork.services

import cn.yangchengyu.libcommon.model.User
import cn.yangchengyu.libnetwork.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/28
 */

interface LoginService {
    /**
     * 向后端注册插入用户
     * */
    @GET("/serverdemo/user/insert")
    suspend fun insertUser(
        @Query("name") nickname: String,
        @Query("avatar") avatar: String,
        @Query("qqOpenId") openId: String,
        @Query("expires_time") expiresTime: Long
    ): ApiResponse<User?>
}