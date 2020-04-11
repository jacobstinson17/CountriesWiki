package com.jacobstinson.countrieswiki.model.util

import androidx.annotation.Nullable

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

class Resource<T>(val status: Status, val data: T?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data
            )
        }

        fun <T> error(@Nullable data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data
            )
        }

        fun <T> loading(@Nullable data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data
            )
        }
    }

}