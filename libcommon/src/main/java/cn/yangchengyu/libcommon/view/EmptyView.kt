package cn.yangchengyu.libcommon.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import cn.yangchengyu.libcommon.R

/**
 * Desc  : 空页面
 * Author: Chengyu Yang
 * Date  : 2020/4/5
 */
class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val emptyIcon: ImageView
    private val emptyContent: TextView
    private val emptyButton: Button

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        View.inflate(context, R.layout.layout_empty_view, this)

        emptyIcon = findViewById(R.id.empty_icon)
        emptyContent = findViewById(R.id.empty_text)
        emptyButton = findViewById(R.id.empty_action)
    }

    fun setEmptyIcon(@DrawableRes iconRes: Int) {
        emptyIcon.setImageResource(iconRes)
    }

    fun getEmptyIcon(): ImageView? = emptyIcon

    fun setEmptyContent(text: String?) {
        text?.takeIf { content ->
            content.isNotEmpty()
        }?.let {
            emptyContent.text = it
            emptyContent.visibility = View.VISIBLE
        }?.run {
            emptyContent.visibility = View.GONE
        }
    }

    fun getEmptyContent(): TextView? = emptyContent

    fun setEmptyButton(text: String?, listener: OnClickListener?) {
        text?.takeIf { buttonText ->
            buttonText.isNotEmpty()
        }?.let {
            emptyButton.text = it
            emptyButton.visibility = View.VISIBLE
            emptyButton.setOnClickListener(listener)
        } ?: run {
            emptyButton.visibility = View.GONE
        }
    }

    fun getEmptyButton(): Button? = emptyButton
}