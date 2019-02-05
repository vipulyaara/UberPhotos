package com.uber.user.data

import com.uber.data.api.Response
import com.uber.data.model.Photos

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
class PhotosRepository(
    private val photosDataSource: PhotosDataSource
) {

    fun fetchPhotos(query: String, page: Int): Response<Photos> {
        return photosDataSource.fetchPhotos(query, page)
    }
}
