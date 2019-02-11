package com.uber.user

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.uber.data.AppExecutors
import com.uber.data.api.Response
import com.uber.data.api.Success
import com.uber.data.model.Photos
import com.uber.user.DependencyProvider.photoDataSource
import com.uber.user.data.PhotosRepository
import com.uber.user.ui.photos.PhotosViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.concurrent.Executor

/**
 * @author Vipul Kumar; dated 08/02/19.
 */
@RunWith(JUnit4::class)
class PhotosViewModelTest {

    private val keyword = "kitten"
    private val page = 1
    private lateinit var viewModel: PhotosViewModel
    private lateinit var photosRepository: PhotosRepository

    @Before
    fun setup() {
        AppExecutors.mainThread = Executor { it.run() }
        photosRepository = mock {
//            onGeneric { fetchPhotos(keyword, page) } doReturn photosResponseSuccess
        }
        photosRepository.photosDataSource = photoDataSource
        viewModel = PhotosViewModel(photosRepository)
    }

    @Test
    fun `success response in viewmodel`() {
        val mockSuccess = mock<(Success<Photos>) -> Unit>()
//        whenever(photosRepository.fetchPhotos(keyword, page, mockSuccess, {})).doReturn(photosResponseSuccess)
        viewModel.fetchPhotos(keyword, page, mockSuccess, {  })

        Thread.sleep(4000) // Wait network to finish call the ugly way

        verify(mockSuccess).invoke(photosResponseSuccess)
    }
}
