package com.uber.user

import com.uber.data.api.ErrorResponse
import com.uber.data.api.Success
import com.uber.data.model.Photo
import com.uber.data.model.Photos

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
fun photoList(): ArrayList<Photo> {
    val photos = arrayListOf<Photo>()
    repeat(20) {
        photos.add(
            Photo(
                id = it.toString()
            )
        )
    }
    return photos
}

val photos = Photos(
    page = 1,
    pages = "",
    perPage = 10,
    photo = photoList()
)

val photosResponseSuccess = Success(photos)

val photosResponseError = ErrorResponse<Photos>(RuntimeException("unknown exception"))
