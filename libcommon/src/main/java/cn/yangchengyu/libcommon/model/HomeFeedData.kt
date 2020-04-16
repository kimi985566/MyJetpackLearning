package cn.yangchengyu.libcommon.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/15
 */

@Parcelize
data class HomeFeedData(
    var data: List<Feed?>? = null
) : Parcelable, Serializable