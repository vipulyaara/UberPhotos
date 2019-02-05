package com.uber.data.dali

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Vipul Kumar; dated 04/02/19.
 *
 * Utility to fetch image from network.
 */
object ImageFetcher {
    fun decodeSampledBitmapFromUrl(url: URL, reqWidth: Int, reqHeight: Int): Bitmap? {

        var input: InputStream? = getHTTPConnectionInputStream(url)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)

        if (reqHeight != 0) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        }

        try {
            input?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        input = getHTTPConnectionInputStream(url)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeStream(input, null, options)
    }

    private fun getHTTPConnectionInputStream(url: URL): InputStream? {
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            return connection.inputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
