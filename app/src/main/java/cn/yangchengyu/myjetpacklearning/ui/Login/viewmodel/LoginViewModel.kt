package cn.yangchengyu.myjetpacklearning.ui.Login.viewmodel

import androidx.lifecycle.ViewModel
import cn.yangchengyu.myjetpacklearning.ui.Login.repository.LoginRepository

/**
 * Desc  : 登陆ViewModel
 * Author: Chengyu Yang
 * Date  : 2020/4/28
 */

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun login() {
        repository.login()
    }
}