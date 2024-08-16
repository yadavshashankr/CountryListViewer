package com.shashank.countrylist.core.utils

/**
 * 'NetworkResult' is a generic class helping in retrieving and distributing received data, error
 * across the project
 */

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : NetworkResult<T?>(data)
    class Error<T>(message: String = "", data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
}