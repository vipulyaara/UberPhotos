package com.uber.data.parser

import com.uber.data.model.Photo
import com.uber.data.model.Photos
import com.uber.data.model.PhotosResponse
import org.json.JSONException
import org.json.JSONObject

/**
 * Utilities for converting json response into [PhotosResponse]
 */
@Throws(JSONException::class)
fun JSONObject.toPhotoResponse(): PhotosResponse {
    val photosResponse = PhotosResponse()
    try {
        photosResponse.stat = this.getString("stat")
        photosResponse.photos = this.getJSONObject("photos").toPhotos()
    } catch (e: JSONException) {
        throw e
    }
    return photosResponse
}

@Throws(JSONException::class)
fun JSONObject.toPhotos(): Photos {
    val photos = Photos()
    try {
        photos.page = this.getInt("page")
        photos.pages = this.getString("pages")
        photos.perPage = this.getInt("perpage")
        val array = this.getJSONArray("photo")
        val list = arrayListOf<Photo>()
        for (i in 0 until array.length()) {
            list.add(array.getJSONObject(i).toPhoto())
        }
        photos.photo = list
    } catch (e: JSONException) {
        throw e
    }
    return photos
}

@Throws(JSONException::class)
fun JSONObject.toPhoto(): Photo {
    val photo = Photo()
    try {
        photo.title = this.getString("title")
        photo.server = this.getString("server")
        photo.farm = this.getInt("farm")
        photo.secret = this.getString("secret")
        photo.id = this.getString("id")
    } catch (e: JSONException) {
        throw e
    }
    return photo
}
