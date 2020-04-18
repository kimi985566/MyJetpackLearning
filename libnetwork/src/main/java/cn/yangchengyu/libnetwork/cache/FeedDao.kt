package cn.yangchengyu.libnetwork.cache

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cn.yangchengyu.libcommon.model.Feed

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/4/16
 */

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeds(posts: List<Feed>?)

    @Query("SELECT * FROM feeds ORDER BY createTime DESC")
    fun getFeeds(): DataSource.Factory<Int, Feed>

    @Query("DELETE FROM feeds")
    fun deleteAllFeeds()
}