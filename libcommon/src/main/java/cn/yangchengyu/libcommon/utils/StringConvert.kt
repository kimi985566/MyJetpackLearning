package cn.yangchengyu.libcommon.utils

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/29
 */
object StringConvert {

    @JvmStatic
    fun convertFeedUgc(count: Int): String = when {
        count < 10000 -> count.toString()
        else -> String.format("%.2f万", (count / 10000).toDouble())
    }

    @JvmStatic
    fun convertTagFeedList(num: Int): String = when {
        num < 10000 -> String.format("%s人观看", num.toString())
        else -> String.format("%.2f万人观看", (num / 10000).toDouble())
    }

    @JvmStatic
    fun convertSpannable(count: Int, intro: String): CharSequence =
        SpannableString(String.format("%s%s", count.toString(), intro)).apply {
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                count.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                AbsoluteSizeSpan(16, true),
                0,
                count.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                count.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
}