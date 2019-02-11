package com.uber.user

import com.uber.data.api.LoggingInterceptor
import com.uber.data.api.UberNetworking
import com.uber.data.mapper.PhotosResponseMapper
import com.uber.user.data.PhotosDataSource
import com.uber.user.data.PhotosRepository

/**
 * @author Vipul Kumar; dated 04/02/19.
 *
 * The simplest dependency provider.
 */
object DependencyProvider {
    val photoResponseMapper = PhotosResponseMapper()
    val photoDataSource = PhotosDataSource()
    var photoRepository = PhotosRepository(photoDataSource)
}
