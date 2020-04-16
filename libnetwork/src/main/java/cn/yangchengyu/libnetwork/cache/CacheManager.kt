package cn.yangchengyu.libnetwork.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {

    @JvmStatic
    fun <T> delete(key: String?, body: T) {
        val cache = Cache()
        if (!key.isNullOrEmpty()) {
            cache.key = key
        }
        cache.data = toByteArray(body)

        CacheDatabase.get().cache.delete(cache)
    }

    @JvmStatic
    fun <T> save(key: String?, body: T) {
        val cache = Cache()
        if (!key.isNullOrEmpty()) {
            cache.key = key
        }
        cache.data = toByteArray(body)

        CacheDatabase.get().cache.save(cache)
    }

    @JvmStatic
    fun getCache(key: String?): Any? {
        if (key.isNullOrEmpty()) {
            return null
        }

        return CacheDatabase.get()?.cache?.getCache(key)?.data?.run {
            toObject(this)
        } ?: run {
            null
        }
    }

    /**
     * 反序列,把二进制数据转换成java object对象
     * */
    @JvmStatic
    private fun toObject(data: ByteArray): Any? {
        var byteArrayInputStream: ByteArrayInputStream? = null
        var objectInputStream: ObjectInputStream? = null

        try {
            byteArrayInputStream = ByteArrayInputStream(data)
            objectInputStream = ObjectInputStream(byteArrayInputStream)

            return objectInputStream.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                byteArrayInputStream?.close()
                objectInputStream?.close()
            } catch (ignore: Exception) {
                ignore.printStackTrace()
            }
        }

        return null
    }

    /**
     * 序列化存储数据需要转换成二进制
     * */
    @JvmStatic
    private fun <T> toByteArray(body: T): ByteArray {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var objectOutputStream: ObjectOutputStream? = null

        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)

            objectOutputStream.writeObject(body)
            objectOutputStream.flush()

            return byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                byteArrayOutputStream?.close()
                objectOutputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return ByteArray(0)
    }
}