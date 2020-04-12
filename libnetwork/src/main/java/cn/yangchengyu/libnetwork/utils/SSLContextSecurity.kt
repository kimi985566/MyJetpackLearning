package cn.yangchengyu.libnetwork.utils

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */

object SSLContextSecurity {

    @JvmStatic
    fun createIgnoreVerifySSL(sslVersion: String): SSLSocketFactory {
        //ssl
        val ssl = SSLContext.getInstance(sslVersion)
        //http 证书问题
        val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
            @Throws(Exception::class)
            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            @Throws(Exception::class)
            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        })
        //init
        ssl.init(null, trustAllCerts, SecureRandom())
        //如果 hostname in certificate didn't match的话就给一个默认的主机验证
        HttpsURLConnection.setDefaultSSLSocketFactory(ssl.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { _: String?, _: SSLSession? -> true }

        return ssl.socketFactory
    }
}