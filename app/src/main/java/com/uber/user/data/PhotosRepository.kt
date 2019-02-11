package com.uber.user.data

import com.uber.data.AppExecutors
import com.uber.data.AppExecutors.mainThread
import com.uber.data.AppExecutors.networkIO
import com.uber.data.api.ErrorResponse
import com.uber.data.api.Response
import com.uber.data.api.Success
import com.uber.data.model.Photos

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
class PhotosRepository(
    var photosDataSource: PhotosDataSource // var for testing
) {

    fun fetchPhotos(
        query: String,
        page: Int,
        success: (success: Success<Photos>) -> Unit,
        error: (error: ErrorResponse<Photos>) -> Unit) {
        networkIO.execute {
            val result = photosDataSource.fetchPhotos(query, page)

            // because we cannot use liveData
            mainThread.execute {
                if (result is Success) success.invoke(result)
                if (result is ErrorResponse) error.invoke(result)
            }
        }
    }
}
