package com.uber.data.dali

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import androidx.collection.LruCache

/**
 * @author Vipul Kumar; dated 04/02/19.
 *
 * Implementation of [LruCache] for in memory image cache.
 */
class ImageCache(maxSize: Int) : LruCache<String, Bitmap>(maxSize) {

    override fun sizeOf(key: String, value: Bitmap): Int {
        return value.rowBytes * value.height
    }

    companion object {
        private var instance: ImageCache? = null

        operator fun get(context: Context?): ImageCache {
            if (instance == null) {
                val am = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val memClassBytes = (am.memoryClass * 1024 * 1024).toFloat()
                instance = ImageCache(Math.round(memClassBytes))
            }
            return instance as ImageCache
        }
    }
}
