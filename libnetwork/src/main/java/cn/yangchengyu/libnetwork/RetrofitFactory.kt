package cn.yangchengyu.libnetwork

import cn.yangchengyu.libcommon.utils.AppGlobals
import cn.yangchengyu.libnetwork.common.NetworkBaseContent
import cn.yangchengyu.libnetwork.intercepter.CustomCacheInterceptor
import cn.yangchengyu.libnetwork.services.HomeService
import cn.yangchengyu.libnetwork.services.LoginService
import cn.yangchengyu.libnetwork.utils.SSLContextSecurity
import com.blankj.utilcode.util.LogUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

object RetrofitFactory {

    private val mRetrofit: Retrofit
    private val mInterceptor: Interceptor

    //cookie持久化
    private val cookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(AppGlobals.getApplication().applicationContext)
        )
    }

    //首页服务
    val homeService by lazy {
        create(HomeService::class.java)
    }

    //登陆服务
    val loginService by lazy {
        create(LoginService::class.java)
    }

    init {
        //通用拦截
        mInterceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content_Type", "application/json")
                .addHeader("charset", "UTF-8")
                .build()

            chain.proceed(request)
        }

        //http 证书问题
        try {
            SSLContextSecurity.createIgnoreVerifySSL("SSL")
        } catch (e: Exception) {
            LogUtils.e(this.javaClass.simpleName, e)
        }

        //retrofit builder
        mRetrofit = Retrofit.Builder()
            .baseUrl(NetworkBaseContent.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(initClient())
            .build()
    }

    /**
     * OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(initLogInterceptor())
            .addInterceptor(CustomCacheInterceptor())
            .addInterceptor(mInterceptor)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    /**
     * 日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     *具体服务实例化
     */
    private fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}