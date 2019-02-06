package com.uber.data

import com.uber.data.api.LoggingInterceptor
import com.uber.data.api.Request
import com.uber.data.api.UberNetworking
import com.uber.data.model.Photo

/**
 * @author Vipul Kumar; dated 31/01/19.
 *
 * Utilities specific to Flickr api.
 */
private const val baseUrl = "https://api.flickr.com/"
private const val apiKey = "3e7cc266ae2b0e0d78e279ce8e361736"
const val photosPerPage = 10

fun Photo.flickrImageUrl() =
    "https://farm${this.farm}.staticflickr.com/${this.server}/${this.id}_${this.secret}_q.jpg"

fun fetchImagesUrl(query: String, page: Int) = baseUrl +
        "services/rest/?method=flickr.photos.search" +
        "&api_key=$apiKey" +
        "&format=json" +
        "&nojsoncallback=1" +
        "&safe_search=1" +
        "&tags=$query" +
        "&per_page=$photosPerPage" +
        "&page=$page"

fun fetchImagesRequest(query: String, page: Int) =
    Request(url = fetchImagesUrl(query, page))
