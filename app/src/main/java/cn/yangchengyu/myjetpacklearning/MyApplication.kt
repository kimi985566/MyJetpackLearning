package cn.yangchengyu.myjetpacklearning

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import cn.yangchengyu.libnetwork.ApiService
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils

/**
 * Desc  : Application
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        //注册AndroidCode工具
        initAndroidCodeUtils()
        //注册网络工具
        ApiService.init("http://123.56.232.18:8080/serverdemo", null)
        //注册生命周期回调
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        //非默认值
        if (newConfig.fontScale != 1f) {
            resources
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        //非默认值
        if (res.configuration.fontScale != 1f) {
            val newConfig = Configuration()
            newConfig.setToDefaults() //设置默认
            res.updateConfiguration(newConfig, res.displayMetrics)
        }
        return res
    }

    private fun initAndroidCodeUtils() {
        Utils.init(this)
    }

    private val lifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            LogUtils.d(activity.componentName.className + " " + "onCreated")
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
            LogUtils.d(activity.componentName.className + " " + "onResume")
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            LogUtils.d(activity.componentName.className + " " + "onDestroy")
        }
    }
}