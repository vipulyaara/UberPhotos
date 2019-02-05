package com.uber.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.uber.data.api.ErrorResponse
import com.uber.data.api.Response
import com.uber.data.api.Success
import com.uber.data.model.Photos
import com.uber.user.data.PhotosDataSource
import com.uber.user.data.PhotosRepository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
class PhotosRepositoryTest : BaseDataTest() {
    private lateinit var dataSource: PhotosDataSource
    private lateinit var repository: PhotosRepository
    private val keyword = "kitten"
    private val page = 1

    override fun setup() {
        super.setup()
        dataSource = mock()
        repository = PhotosRepository(dataSource)
    }

    @Test
    fun getData_withSuccess() {
        // When requesting the photos
//        val result = photosResponse
        // Given that the dataSource responds with success
        whenever(dataSource.fetchPhotos(keyword, page)).thenReturn(photosResponse)

        val result = repository.fetchPhotos(keyword, page)

        // Then there's one request to the dataSource
        Mockito.verify(dataSource).fetchPhotos(keyword, page)
        // Then the correct set of photos is returned
        Assert.assertEquals(Success(photos), result)
    }
    @Test
    fun getData_withError() {
        val exception = RuntimeException()
        val errorResult: Response<Photos> = ErrorResponse(exception)
        whenever(dataSource.fetchPhotos(keyword, 1)).thenReturn(errorResult)

        val result = repository.fetchPhotos(keyword, page)

        // Then there's one request to the dataSource
        Mockito.verify(dataSource).fetchPhotos(keyword, page)
        // Then the correct set of photos is returned
        Assert.assertEquals(errorResult, result)
    }
}
