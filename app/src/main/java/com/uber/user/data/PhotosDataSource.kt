package com.uber.user.data

import com.uber.data.api.Response
import com.uber.data.fetchImagesRequest
import com.uber.data.mapper.ResponseMapper
import com.uber.data.model.Photos
import com.uber.data.uberNetworking
import com.uber.user.DependencyProvider.photoResponseMapper

/**
 * @author Vipul Kumar; dated 30/01/19.
 *
 * Data source responsible for fetching photos.
 */
class PhotosDataSource {
    var mapper: ResponseMapper<Photos> = photoResponseMapper

    fun fetchPhotos(query: String, page: Int): Response<Photos> {
        return uberNetworking.execute(
            fetchImagesRequest(query, page), mapper
        )
    }
}
