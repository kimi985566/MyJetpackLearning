package cn.yangchengyu.libnetwork.cache;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import cn.yangchengyu.libcommon.model.Feed;
import cn.yangchengyu.libcommon.utils.AppGlobals;

//数据读取、存储时数据转换器,比如将写入时将Date转换成Long存储，读取时把Long转换Date返回
@TypeConverters(DateConverter.class)
@Database(entities = {Cache.class, Feed.class}, version = 1, exportSchema = false)
public abstract class CacheDatabase extends RoomDatabase {

    private static final CacheDatabase DATABASE;

    static {
        //创建一个内存数据库
        //但是这种数据库的数据只存在于内存中，也就是进程被杀之后，数据随之丢失
        //Room.inMemoryDatabaseBuilder()
        DATABASE = Room.databaseBuilder(AppGlobals.getApplication(), CacheDatabase.class, "learning_jetpack_cache.db")
                //是否允许在主线程进行查询
                .allowMainThreadQueries()
                //数据库创建和打开后的回调
                //.addCallback()
                //设置查询的线程池
                //.setQueryExecutor()
                //.openHelperFactory()
                //room的日志模式
                //.setJournalMode()
                //数据库升级异常之后的回滚
                //.fallbackToDestructiveMigration()
                //数据库升级异常后根据指定版本进行回滚
                //.fallbackToDestructiveMigrationFrom()
                // .addMigrations(CacheDatabase.sMigration)
                .build();

    }

    public abstract CacheDao getCache();

    public abstract FeedDao getFeed();

    public static CacheDatabase get() {
        return DATABASE;
    }
}
