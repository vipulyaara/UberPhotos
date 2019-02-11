package com.uber.data.api

import com.uber.data.BuildConfig
import com.uber.data.mapper.ResponseMapper
import java.lang.RuntimeException
import java.net.URL

/**
 * @author Vipul Kumar; dated 03/02/19.
 *
 * This is our networking framework.
 *
 * It is written with [readText] from kotlin standard library;
 * rather than writing boilerplate for parsing the stream and reader.
 */
open class UberNetworking {
    var interceptors = arrayListOf<Interceptor>()

    fun addInterceptor(interceptor: Interceptor) {
        interceptors.add(interceptor)
    }

    fun <T> execute(request: Request, responseMapper: ResponseMapper<T>): Response<T> {
        interceptors.forEach { it.interceptRequest(request) }
        return try {
            val rawResponse = URL(request.url).readText()
            val response: Response<T> = Success(responseMapper.map(rawResponse))
            response.also { response1 ->
                response1.rawResponse = rawResponse
                interceptors.forEach { it.interceptResponse(response1) }
            }
        } catch (e: Exception) {
            val error = if (BuildConfig.DEBUG) e.message else "An error occurred in the API"
            ErrorResponse(RuntimeException(error))
        }
    }
}
