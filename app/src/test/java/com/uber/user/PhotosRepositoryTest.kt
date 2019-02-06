package com.uber.user

import com.uber.data.api.ErrorResponse
import com.uber.data.api.Response
import com.uber.data.api.Success
import com.uber.data.model.Photos
import com.uber.user.DependencyProvider.photoResponseMapper
import com.uber.user.data.PhotosDataSource
import com.uber.user.data.PhotosRepository
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
@RunWith(JUnit4::class)
class PhotosRepositoryTest {
    private lateinit var dataSource: PhotosDataSource
    private lateinit var repository: PhotosRepository
    private val keyword = "kitten"
    private val page = 1

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()

    @Before
    fun setup() {
        dataSource = mock(PhotosDataSource::class.java)
//        `when`(dataSource.getMappers()).thenReturn(photoResponseMapper)
        repository = PhotosRepository(dataSource)
    }

    @Test
    fun getData_withSuccess() {
        // When requesting the photos
//        val result = photosResponse
        // Given that the dataSource responds with success
        `when`(dataSource.fetchPhotos(keyword, page)).thenReturn(photosResponse)

        val result = repository.fetchPhotos(keyword, page)

        // Then there's one request to the dataSource
//        Mockito.verify(dataSource).fetchPhotos(keyword, page)
        // Then the correct set of photos is returned
        Assert.assertEquals(Success(photos), result)
    }

    @Test
    fun getData_withError() {
        val exception = RuntimeException()
        val errorResult: Response<Photos> = ErrorResponse(exception)
        `when`(dataSource.fetchPhotos(keyword, 1)).thenReturn(errorResult)

        val result = repository.fetchPhotos(keyword, page)

        // Then there's one request to the dataSource
//        Mockito.verify(dataSource).fetchPhotos(keyword, page)
        // Then the correct set of photos is returned
        Assert.assertEquals(errorResult, result)
    }
}
