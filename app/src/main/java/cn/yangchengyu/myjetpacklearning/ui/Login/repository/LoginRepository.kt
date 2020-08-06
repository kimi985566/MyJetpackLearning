package cn.yangchengyu.myjetpacklearning.ui.Login.repository

import android.app.Activity
import androidx.lifecycle.liveData
import cn.yangchengyu.libnetwork.services.LoginService
import cn.yangchengyu.myjetpacklearning.ext.executeResponse
import cn.yangchengyu.myjetpacklearning.ext.tryCatchLaunch
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.connect.UserInfo
import com.tencent.connect.auth.QQToken
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONException
import org.json.JSONObject

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/28
 */

class LoginRepository(
    private val activity: Activity,
    private val loginService: LoginService
) {

    private var tencent: Tencent? = null

    fun login() = liveData<Boolean> {
        if (tencent == null) {
            tencent = Tencent.createInstance("1105739457", activity)
        }

        tencent?.takeIf { it.isSessionValid }?.login(activity, "all", loginListener)
    }

    private var loginListener: IUiListener = object : IUiListener {
        override fun onComplete(any: Any?) {
            try {
                (any as? JSONObject)?.let { response ->
                    val openid = response.getString("openid")
                    val accessToken = response.getString("access_token")
                    val expiresIn = response.getString("expires_in")
                    val expiresTime = response.getLong("expires_time")

                    tencent?.openId = openid
                    tencent?.setAccessToken(accessToken, expiresIn)

                    getUserInfo(tencent?.qqToken, expiresTime, openid)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        override fun onError(uiError: UiError?) {
            ToastUtils.showShort("登陆失败 reason: ${uiError?.toString()}")
        }

        override fun onCancel() {
            ToastUtils.showShort("登陆取消")
        }
    }

    private fun getUserInfo(qqToken: QQToken?, expiresTime: Long, openid: String) {
        val userInfo = UserInfo(activity, qqToken)
        userInfo.getUserInfo(object : IUiListener {
            override fun onComplete(any: Any?) {
                try {
                    (any as? JSONObject)?.let { response ->
                        val nickname = response.getString("nickname")
                        val avatar = response.getString("figureurl_2")

                        saveUserInfo(nickname, avatar, openid, expiresTime)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onError(uiError: UiError?) {
                ToastUtils.showShort("登陆失败 reason: ${uiError?.toString()}")
            }

            override fun onCancel() {
                ToastUtils.showShort("登陆取消")
            }
        })
    }

    private fun saveUserInfo(nickname: String, avatar: String, openid: String, expiresTime: Long) {
        tryCatchLaunch(
            tryBlock = {
                val loginResponse = loginService.insertUser(
                    nickname,
                    avatar,
                    openid,
                    expiresTime
                )

                executeResponse(
                    response = loginResponse,
                    successBlock = { responseData ->
                        LogUtils.i(responseData.toString())
                    },
                    errorBlock = { errorMsg ->
                        ToastUtils.showShort("登陆失败, msg: $errorMsg")
                    }
                )
            },
            catchBlock = { e ->
                e.printStackTrace()
            }
        )
    }
}