package cn.yangchengyu.myjetpacklearning.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import cn.yangchengyu.libcommon.model.Feed
import cn.yangchengyu.libcommon.ui.AbsPagedListAdapter
import cn.yangchengyu.myjetpacklearning.R
import cn.yangchengyu.myjetpacklearning.ui.home.adapter.viewholder.ViewHolderForHomeFeed

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */

class HomeFeedAdapter(context: Context) :
    AbsPagedListAdapter<Feed, ViewHolderForHomeFeed>(
        diffCallback
    ) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Feed>() {
            override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem == newItem
        }
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder2(parent: ViewGroup?, viewType: Int): ViewHolderForHomeFeed {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return ViewHolderForHomeFeed(
            binding.root,
            binding
        )
    }

    override fun onBindViewHolder2(holder: ViewHolderForHomeFeed?, position: Int) {
        when (val item = getItem(position)) {
            is Feed -> {
                holder?.bindData(item)
            }
        }
    }

    override fun getItemViewType2(position: Int): Int {
        return when (val item = getItem(position)) {
            is Feed -> {
                when (item.itemType) {
                    Feed.TYPE_IMAGE_TEXT -> R.layout.layout_feed_type_image
                    Feed.TYPE_VIDEO -> R.layout.layout_feed_type_video
                    else -> super.getItemViewType2(position)
                }
            }
            else -> {
                super.getItemViewType2(position)
            }
        }
    }

}