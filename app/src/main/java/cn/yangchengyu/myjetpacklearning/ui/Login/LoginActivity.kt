package cn.yangchengyu.myjetpacklearning.ui.Login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cn.yangchengyu.myjetpacklearning.R
import cn.yangchengyu.myjetpacklearning.ui.Login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_layout_login.*


/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/27
 */

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_login)

        //登陆ViewModel
        loginViewModel = ViewModelProvider(this, LoginInjection.provideLoginModelFactory(this))
            .get(LoginViewModel::class.java)

        //设置点击事件
        initClickListener()
    }

    private fun initClickListener() {
        //关闭按钮
        actionClose?.setOnClickListener {
            finish()
        }
        //登陆按钮
        actionLogin?.setOnClickListener {
            loginViewModel.login()
        }
    }
}