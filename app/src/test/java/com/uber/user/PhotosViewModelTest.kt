package com.uber.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.uber.data.AppExecutors.mainThread
import com.uber.data.api.ErrorResponse
import com.uber.data.api.Success
import com.uber.data.model.Photos
import com.uber.user.DependencyProvider.photoRepository
import com.uber.user.data.PhotosDataSource
import com.uber.user.data.PhotosRepository
import com.uber.user.ui.photos.PhotosViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.Executor

/**
 * @author Vipul Kumar; dated 08/02/19.
 */
@RunWith(JUnit4::class)
class PhotosViewModelTest {

    private val keyword = "kitten"
    private val page = 1
    private lateinit var viewModel: PhotosViewModel
    private lateinit var dataSource: PhotosDataSource

    @Before
    fun setup() {
        dataSource = mock()
        photoRepository = PhotosRepository(dataSource)
        viewModel = PhotosViewModel()
        mainThread = Executor { it.run() }
    }

    @Test
    fun `get view model data with success`() {
        val photosResponseSuccess = Success(photos)

        // return success when called
        whenever(dataSource.fetchPhotos(keyword, page)).thenReturn(photosResponseSuccess)
        val mockSuccess = mock<(Success<Photos>) -> Unit>()

        viewModel.fetchPhotos(keyword, page, mockSuccess, { })

        // verify interaction
        verify(mockSuccess).invoke(photosResponseSuccess)
    }

    @Test
    fun `get repository data with error`() {
        val errorResponse = ErrorResponse<Photos>(Exception())
        // return error when called
        whenever(dataSource.fetchPhotos("", page)).thenReturn(errorResponse)

        val mockError = mock<(ErrorResponse<Photos>) -> Unit>()
        viewModel.fetchPhotos("", page, {}, mockError)

        // verify interaction
        verify(mockError).invoke(errorResponse)
    }
}
