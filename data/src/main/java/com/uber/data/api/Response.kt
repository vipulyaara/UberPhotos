package com.uber.data.api
/**
 * @author Vipul Kumar; dated 04/02/19.
 *
 * Data object that returns [Success] in case of an api success;
 * and [ErrorResponse] in case of error error.
 */
sealed class Response<T> {
    open fun get(): T? = null
    var rawResponse: String? = null

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is ErrorResponse -> "Error[exception=$exception]"
        }
    }
}

data class Success<T>(val data: T) : Response<T>() {
    override fun get(): T = data
}

data class ErrorResponse<T>(val exception: Exception) : Response<T>()
