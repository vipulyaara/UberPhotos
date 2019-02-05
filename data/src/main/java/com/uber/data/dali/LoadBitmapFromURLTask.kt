package com.uber.data.dali

import android.view.View
import android.widget.ImageView
import com.uber.data.AppExecutors.imagesIO
import com.uber.data.AppExecutors.mainThread
import java.net.MalformedURLException
import java.net.URL

/**
 * @author Vipul Kumar; dated 04/02/19.
 *
 * Task used to load images from network.
 * It takes care of threading efforts and uses [ImageFetcher] to fetch image from url.
 */
class LoadBitmapFromURLTask(
    private val imageView: ImageView,
    private val tag: String,
    private val mCache: ImageCache?,
    private val strUrl: String
) {

    fun execute() {
        imagesIO.execute {
            var url: URL? = null
            try {
                url = URL(strUrl)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            if (url != null) {
                val bitmap =
                    ImageFetcher.decodeSampledBitmapFromUrl(url, imageView.width, imageView.height)
                bitmap?.let {
                    mainThread.execute {
                        if (imageView.tag == tag) {
                            imageView.visibility = View.INVISIBLE
                            mCache?.put(strUrl, it)
                            imageView.setImageBitmap(it)
                            imageView.visibility = View.VISIBLE // for animation
                        }
                    }
                }
            }
        }
    }
}

