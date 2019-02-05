package com.uber.data.api

import android.util.Log

/**
 * @author Vipul Kumar; dated 03/02/19.
 *
 * Logs the [Request] and [Response].
 */
class LoggingInterceptor : Interceptor() {
    override fun interceptRequest(request: Request): Request {
        Log.d("${javaClass.simpleName} request", request.url)
        return request
    }

    override fun <T> interceptResponse(response: Response<T>): Response<T> {
        Log.d("${javaClass.simpleName} response", "$response")
        return response
    }
}
