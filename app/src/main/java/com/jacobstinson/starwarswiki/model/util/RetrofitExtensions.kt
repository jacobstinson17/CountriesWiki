package com.jacobstinson.starwarswiki.model.util

import retrofit2.Response

/**
 * Converts Retrofit [Response] to [Resource] which provides state
 * and data to the UI.
 */
fun <ResultType> Response<ResultType>.toResource(): Resource<ResultType> {
    val error = errorBody()?.toString() ?: message()
    return when {
        isSuccessful -> {
            val body = body()
            when {
                body != null -> Resource.success(body)
                else -> Resource.error(null as ResultType)
            }
        }
        else -> Resource.error(null as ResultType)
    }
}