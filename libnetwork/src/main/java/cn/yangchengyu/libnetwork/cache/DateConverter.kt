package cn.yangchengyu.libnetwork.cache

import androidx.room.TypeConverter
import java.util.*

object DateConverter {
    @TypeConverter
    fun date2Long(date: Date): Long = date.time

    @TypeConverter
    fun long2Date(data: Long): Date = Date(data)
}