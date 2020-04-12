package cn.yangchengyu.libcommon.utils

import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import cn.yangchengyu.libcommon.R

/**
 * Desc  : 用于设置圆角等
 * Author: Chengyu Yang
 * Date  : 2020/3/23
 */
object ViewHelper {

    const val RADIUS_ALL = 0
    const val RADIUS_LEFT = 1
    const val RADIUS_TOP = 2
    const val RADIUS_RIGHT = 3
    const val RADIUS_BOTTOM = 4

    @JvmStatic
    fun setViewOutline(view: View, attributes: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        view.context.theme.obtainStyledAttributes(
            attributes,
            R.styleable.viewOutLineStrategy,
            defStyleAttr,
            defStyleRes
        ).apply {
            var radius = 0
            var hideSide = 0

            try {
                radius = getDimensionPixelSize(R.styleable.viewOutLineStrategy_clip_radius, 0)
                hideSide = getInt(R.styleable.viewOutLineStrategy_clip_side, 0)
            } finally {
                this.recycle()

                setViewOutline(view, radius, hideSide)
            }
        }
    }

    @JvmStatic
    fun setViewOutline(owner: View, radius: Int, radiusSide: Int) {
        owner.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val w = view.width
                val h = view.height

                if (w == 0 || h == 0) {
                    return
                }

                if (radiusSide != RADIUS_ALL) {
                    var left = 0
                    var top = 0
                    var right = w
                    var bottom = h

                    when (radiusSide) {
                        RADIUS_LEFT -> right += radius
                        RADIUS_TOP -> bottom += radius
                        RADIUS_RIGHT -> left -= radius
                        RADIUS_BOTTOM -> top -= radius
                    }

                    outline.setRoundRect(left, top, right, bottom, radius.toFloat())
                    return
                }

                val top = 0
                val left = 0

                when {
                    radius <= 0 -> outline.setRect(left, top, w, h)
                    else -> outline.setRoundRect(left, top, w, h, radius.toFloat())
                }
            }
        }

        owner.clipToOutline = radius > 0
        owner.invalidate()
    }
}