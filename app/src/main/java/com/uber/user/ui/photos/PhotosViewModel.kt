package com.uber.user.ui.photos

import com.uber.data.AppExecutors.mainThread
import com.uber.data.AppExecutors.networkIO
import com.uber.data.api.ErrorResponse
import com.uber.data.api.Success
import com.uber.data.model.Photos
import com.uber.user.DependencyProvider.photoRepository
import com.uber.user.ui.common.BaseViewModel

/**
 * @author Vipul Kumar; dated 30/01/19.
 *
 * ViewModel to fetch photos and expose the result to fragments.
 */
open class PhotosViewModel : BaseViewModel() {

    fun fetchPhotos(
        query: String,
        page: Int,
        success: (success: Success<Photos>) -> Unit,
        error: (error: ErrorResponse<Photos>) -> Unit
    ) {
        networkIO.execute {
            val result = photoRepository.fetchPhotos(query, page)

            // because we cannot use liveData
            mainThread.execute {
                if (result is Success) success.invoke(result)
                if (result is ErrorResponse) error.invoke(result)
            }
        }
    }
}
