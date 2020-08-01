package com.kulloveth.cardinfofinder.network

import javax.inject.Inject


open class ResponseHandler @Inject constructor() {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(code: Int): Resource<T> {
        return Resource.error(getErrorMessage(code), null)
    }

    private fun getErrorMessage(httpCode: Int): String {
        return when (httpCode) {
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}