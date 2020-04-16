package cn.yangchengyu.myjetpacklearning.ui.home

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import cn.yangchengyu.libcommon.BR
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.myjetpacklearning.databinding.LayoutFeedTypeImageBinding
import cn.yangchengyu.myjetpacklearning.databinding.LayoutFeedTypeVideoBinding

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */

class ViewHolderForHomeFeed(itemView: View, private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(itemView) {

    fun bindData(item: Feed) {
        binding.setVariable(BR.feed, item)
        when (binding) {
            is LayoutFeedTypeImageBinding -> {
                //绑定图片
                binding.feedImage.bindData(item.width, item.height, 16, item.cover)
            }
            is LayoutFeedTypeVideoBinding -> {
                //绑定视频区域
                binding.listPlayerView.bindData(item.width, item.height, item.cover, item.url)
            }
        }
    }

}