package io.infinity.newsapp.services.networkservices



/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

sealed class Resource<out T>(
    val status: Status,
    val data: T?=null,
    val message: String?=null,
    val responseCode: Int?=null,
) {

    class Success<T>(data: T?, responseCode: Int) : Resource<T>(
        Status.SUCCESS,
        data,
        null,
        responseCode
    )

    class Error<T>(msg: String, responseCode: Int) : Resource<T>(
        Status.ERROR,
        message= msg,
        responseCode=responseCode
    )

    class NoInternetConnection<T>(msg: String, data: T?, responseCode: Int) : Resource<T>(
        Status.NO_INTERNET_CONNECTION,
        message=msg,
        responseCode=responseCode
    )

    class Loading<T>() : Resource<T>(
        Status.LOADING
    )
}


