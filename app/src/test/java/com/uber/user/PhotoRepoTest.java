package com.uber.user;

import com.uber.data.api.Response;
import com.uber.data.api.Success;
import com.uber.user.data.PhotosDataSource;
import com.uber.user.data.PhotosRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

/**
 * @author Vipul Kumar; dated 06/02/19.
 */
@RunWith(JUnit4.class)
public class PhotoRepoTest {

    private PhotosDataSource dataSource;
    private PhotosRepository repository;
    private String keyword = "kitten";
    private int page = 1;

    @Before
    public void setup() {
        dataSource = Mockito.mock(PhotosDataSource.class);
        Mockito.when(dataSource.fetchPhotos(keyword, page)).thenReturn(SampleDataKt.getPhotosResponse());
        repository = new PhotosRepository(dataSource);
    }

    @Test
    public void getData_withSuccess() {
        Response result = repository.fetchPhotos(keyword, page);

        Assert.assertEquals(SampleDataKt.getPhotosResponse(), result);
    }
}
