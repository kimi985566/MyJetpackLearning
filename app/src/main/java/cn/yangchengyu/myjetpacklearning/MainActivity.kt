package cn.yangchengyu.myjetpacklearning

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import cn.yangchengyu.myjetpacklearning.utils.NavGraphBuilder
import com.blankj.utilcode.util.BarUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
        setContentView(R.layout.activity_main)

        navController = findNavController(navHostFragment)
        NavigationUI.setupWithNavController(navView, navController)

        NavGraphBuilder.build(navController)

        navView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        navController.navigate(menuItem.itemId)
        return !menuItem.title.isNullOrEmpty()
    }
}
