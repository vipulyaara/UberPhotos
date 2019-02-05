package com.uber.data.api

/**
 * @author Vipul Kumar; dated 03/02/19.
 *
 * Used to intercept [Request] and [Response].
 *
 * Provided implementation is [LoggingInterceptor] to debug networking code.
 */
abstract class Interceptor {
    abstract fun interceptRequest(request: Request): Request
    abstract fun <T> interceptResponse(response: Response<T>): Response<T>
}
