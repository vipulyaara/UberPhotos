package com.uber.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.uber.data.AppExecutors
import com.uber.data.api.ErrorResponse
import com.uber.data.api.Success
import com.uber.data.model.Photos
import com.uber.user.DependencyProvider.photoResponseMapper
import com.uber.user.data.PhotosDataSource
import com.uber.user.data.PhotosRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.Executor

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
@RunWith(JUnit4::class)
class PhotosRepositoryTest {
    private lateinit var dataSource: PhotosDataSource
    private lateinit var repository: PhotosRepository
    private val keyword = "kitten"
    private val page = 1

    @Before
    fun setup() {
        dataSource = mock()
        dataSource.mapper = photoResponseMapper
        repository = PhotosRepository(dataSource)
        AppExecutors.mainThread = Executor { it.run() }
    }

    @Test
    fun `get data with success`() {
        val photosResponseSuccess = Success(photos)

        // return success when called
        whenever(dataSource.fetchPhotos(keyword, page)).thenReturn(photosResponseSuccess)
        val mockSuccess = mock<(Success<Photos>) -> Unit>()

        repository.fetchPhotos(keyword, page, mockSuccess, { })

        // verify interaction
        verify(mockSuccess).invoke(photosResponseSuccess)
    }

    @Test
    fun `get data with error`() {
        val errorResponse = ErrorResponse<Photos>(Exception())
        // return error when called
        whenever(dataSource.fetchPhotos(keyword, page)).thenReturn(errorResponse)

        val mockError = mock<(ErrorResponse<Photos>) -> Unit>()
        repository.fetchPhotos(keyword, page, {}, mockError)

        // verify interaction
        verify(mockError).invoke(errorResponse)
    }
}
