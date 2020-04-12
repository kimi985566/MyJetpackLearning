package cn.yangchengyu.libcommon.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import cn.yangchengyu.libcommon.R
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.layout_player_view.view.*


/**
 * Desc  : 列表视频播放View
 * Author: Chengyu Yang
 * Date  : 2020/4/5
 */

class ListPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int? = 0
) : FrameLayout(context) {

    private var mCategory: String? = null
    private var mVideoUrl: String? = null
    private var isPlaying = false
    private var mWidthPx = 0
    private var mHeightPx = 0

    init {
        View.inflate(context, R.layout.layout_player_view, this)
    }

    fun bindData(category: String, widthPx: Int, heightPx: Int, coverUrl: String?, videoUrl: String) {
        mCategory = category
        mVideoUrl = videoUrl
        mWidthPx = widthPx
        mHeightPx = heightPx

        cover.setImageUrl(coverUrl)

        //如果该视频的宽度小于高度,则高斯模糊背景图显示出来
        if (widthPx < heightPx) {
            PPImageView.setBlurImageUrl(blurBackground, coverUrl, 10)
            blurBackground.visibility = View.VISIBLE
        } else {
            blurBackground.visibility = View.INVISIBLE
        }

        setSize(widthPx, heightPx)
    }

    private fun setSize(widthPx: Int, heightPx: Int) {
        //这里主要是做视频宽大与高,或者高大于宽时  视频的等比缩放
        val maxWidth: Int = ScreenUtils.getScreenWidth()
        val layoutHeight: Int
        val coverWidth: Int
        val coverHeight: Int

        if (widthPx >= heightPx) {
            coverWidth = maxWidth
            coverHeight = (heightPx / (widthPx * 1.0f / maxWidth)).toInt()
            layoutHeight = coverHeight
        } else {
            coverHeight = maxWidth
            layoutHeight = coverHeight
            coverWidth = (widthPx / (heightPx * 1.0f / maxWidth)).toInt()
        }

        val params = layoutParams
        params.width = maxWidth
        params.height = layoutHeight
        layoutParams = params

        val blurParams: ViewGroup.LayoutParams = blurBackground.layoutParams
        blurParams.width = maxWidth
        blurParams.height = layoutHeight
        blurBackground.layoutParams = blurParams

        val coverParams: LayoutParams = cover.layoutParams as LayoutParams
        coverParams.width = coverWidth
        coverParams.height = coverHeight
        coverParams.gravity = Gravity.CENTER
        cover.layoutParams = coverParams

        val playBtnParams: LayoutParams = playBtn.layoutParams as LayoutParams
        playBtnParams.gravity = Gravity.CENTER
        playBtn.layoutParams = playBtnParams
    }

}