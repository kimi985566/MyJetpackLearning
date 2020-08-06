package cn.yangchengyu.myjetpacklearning.ui.Login

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import cn.yangchengyu.libnetwork.RetrofitFactory
import cn.yangchengyu.myjetpacklearning.ui.Login.repository.LoginRepository
import cn.yangchengyu.myjetpacklearning.ui.Login.viewmodel.LoginViewModelFactory

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */
object LoginInjection {

    private fun provideLoginRepository(activity: Activity): LoginRepository {
        return LoginRepository(activity, RetrofitFactory.loginService)
    }

    fun provideLoginModelFactory(activity: Activity): ViewModelProvider.Factory {
        return LoginViewModelFactory(provideLoginRepository(activity))
    }
}