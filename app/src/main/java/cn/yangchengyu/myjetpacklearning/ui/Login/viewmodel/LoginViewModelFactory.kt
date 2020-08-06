package cn.yangchengyu.myjetpacklearning.ui.Login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.yangchengyu.myjetpacklearning.ui.Login.repository.LoginRepository

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/28
 */

class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}