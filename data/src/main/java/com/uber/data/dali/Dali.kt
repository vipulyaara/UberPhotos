package com.uber.data.dali

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import java.lang.ref.WeakReference
import java.security.AccessController.getContext
import java.util.UUID

/**
 * @author Vipul Kumar; dated 04/02/19.
 *
 * Dali is our image fetching and caching framework.
 */
class Dali {
    private var url: String? = null
    private var placeholderRes: Int = 0

    companion object {
        private var contextWeakReference: WeakReference<Context>? = null
        private lateinit var muralWeakReference: WeakReference<Dali>

        fun with(context: Context): Dali {
            contextWeakReference = WeakReference(context)

            val dali = Dali()
            muralWeakReference = WeakReference(dali)

            return getDali()
        }

        private fun getContext(): Context? {
            return contextWeakReference?.get()
        }

        private fun getDali(): Dali {
            return muralWeakReference.get()!!
        }
    }

    fun loadImage(
        imageView: ImageView,
        tag: String = UUID.randomUUID().toString()
    ) {
        imageView.tag = tag
        val imageCache: ImageCache?
        val bitmap: Bitmap?

        imageCache = ImageCache[getContext()]
        var imageKey: String? = null
        if (url != null) {
            imageKey = url
        }
        bitmap = imageCache.get(imageKey ?: throw RuntimeException("Null key"))

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            if (url != null) {
                val task = LoadBitmapFromURLTask(imageView, tag, imageCache, url!!)

                imageView.setImageResource(placeholderRes)

                task.execute()
            }
        }
    }

    fun source(url: String): Dali {
        this.url = url
        return getDali()
    }

    fun placeholder(placeholderRes: Int): Dali {
        this.placeholderRes = placeholderRes
        return getDali()
    }
}
