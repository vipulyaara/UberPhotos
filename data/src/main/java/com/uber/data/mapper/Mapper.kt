package com.uber.data.mapper

import com.uber.data.model.Photos
import com.uber.data.parser.toPhotoResponse
import org.json.JSONObject

/**
 * @author Vipul Kumar; dated 30/01/19.
 */

/**
 * Maps [F] to [T]
 */
interface Mapper<F, T> {
    fun map(from: F): T
}

/**
 * Maps [String] to [T]
 */
interface ResponseMapper<T> : Mapper<String, T>

open class PhotosResponseMapper : ResponseMapper<Photos> {
    override fun map(from: String): Photos {
        return JSONObject(from).toPhotoResponse().photos ?: Photos()
    }
}
