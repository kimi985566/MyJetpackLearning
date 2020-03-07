package cn.yangchengyu.myjetpacklearning.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import cn.yangchengyu.myjetpacklearning.R
import cn.yangchengyu.myjetpacklearning.utils.AppConfig
import com.blankj.utilcode.util.SizeUtils
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class AppBottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private val config = AppConfig.getBottomBarConfig()

    companion object {
        private val iconList = intArrayOf(
            R.drawable.icon_tab_home,
            R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find,
            R.drawable.icon_tab_mine
        )
    }

    init {
        //设置颜色
        initColorState()
        //设置Item
        initMenuItem()
        //此处给按钮icon设置大小
        initMenuItemSize()
        //底部导航栏默认选中项
        initDefaultItem()
    }

    private fun initDefaultItem() {
        if (config.selectTab != 0) {
            val selectTab = config.tabs[config.selectTab]
            if (selectTab.enable) {
                val itemId = getItemId(selectTab.pageUrl)
                /*这里需要延迟一下 再定位到默认选中的tab
                * 因为 咱们需要等待内容区域,也就NavGraphBuilder解析数据并初始化完成，
                * 否则会出现 底部按钮切换过去了，但内容区域还没切换过去*/
                post { selectedItemId = itemId }
            }
        }
    }

    private fun initMenuItem() {
        config.tabs.forEach { tab ->
            if (!tab.enable) {
                return@forEach
            }
            val itemId = getItemId(tab.pageUrl)
            if (itemId < 0) {
                return@forEach
            }

            val menuItem = menu.add(0, itemId, tab.index, tab.title)
            menuItem.setIcon(iconList[tab.index])
        }
    }

    @SuppressLint("RestrictedApi")
    private fun initMenuItemSize() {
        config.tabs.forEachIndexed { index, tab ->
            if (!tab.enable) {
                return@forEachIndexed
            }
            val itemId = getItemId(tab.pageUrl)
            if (itemId < 0) {
                return@forEachIndexed
            }

            val iconSize = SizeUtils.dp2px(tab.size.toFloat())
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(index) as BottomNavigationItemView

            //设置ItemSize
            itemView.setIconSize(iconSize)
            if (tab.title.isNullOrEmpty()) {
                val tintColor = when {
                    tab.tintColor.isNullOrEmpty() -> {
                        Color.DKGRAY
                    }
                    else -> {
                        Color.parseColor(tab.tintColor)
                    }
                }

                //设置Tint颜色
                itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false)
                //Gravity
                itemView.foregroundGravity = Gravity.CENTER
            }
        }
    }

    private fun initColorState() {
        val state = arrayOfNulls<IntArray>(2)
        state[0] = intArrayOf(android.R.attr.state_selected)
        state[1] = intArrayOf()

        val colors = intArrayOf(
            Color.parseColor(config.activeColor),
            Color.parseColor(config.inActiveColor)
        )
        val stateList = ColorStateList(state, colors)

        itemTextColor = stateList
        itemIconTintList = stateList
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
    }

    private fun getItemId(pageUrl: String): Int = AppConfig.getDestConfig()[pageUrl]?.id ?: -1
}