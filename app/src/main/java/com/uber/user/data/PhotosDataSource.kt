package com.uber.user.data

import com.uber.data.api.Response
import com.uber.data.fetchImagesRequest
import com.uber.data.mapper.ResponseMapper
import com.uber.data.model.Photos
import com.uber.user.DependencyProvider.photoResponseMapper
import com.uber.user.DependencyProvider.uberNetworking

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
open class PhotosDataSource(val mapper: ResponseMapper<Photos>) {

    fun fetchPhotos(query: String, page: Int): Response<Photos> {
        return uberNetworking.execute(
            fetchImagesRequest(query, page), mapper
        )
    }
}
