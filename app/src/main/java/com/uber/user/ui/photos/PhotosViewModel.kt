package com.uber.user.ui.photos

import android.util.Log
import com.uber.data.api.ErrorResponse
import com.uber.data.api.Success
import com.uber.data.model.Photos
import com.uber.user.data.PhotosRepository
import com.uber.user.ui.common.BaseViewModel

/**
 * @author Vipul Kumar; dated 30/01/19.
 *
 * ViewModel to fetch photos and expose the result to fragments.
 */
open class PhotosViewModel(private val photosRepository: PhotosRepository) : BaseViewModel() {

    fun fetchPhotos(
        query: String,
        page: Int,
        success: (success: Success<Photos>) -> Unit,
        error: (error: ErrorResponse<Photos>) -> Unit
    ) {
        photosRepository.fetchPhotos(
            query,
            page,
            {
                Log.d(javaClass.simpleName, "fetchPhotos success")
                success.invoke(it)
            },
            {
                Log.d(javaClass.simpleName, "fetchPhotos error")
                error.invoke(it)
            })
    }
}
