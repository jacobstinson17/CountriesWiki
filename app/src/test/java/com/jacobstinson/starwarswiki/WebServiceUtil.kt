package com.jacobstinson.starwarswiki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jacobstinson.starwarswiki.model.util.Resource

object WebServiceUtil {
    fun <T : Any> successCall(data: T) =
        createCall(
            Resource.success(data)
        )

    fun <T : Any> createCall(resource: Resource<T>) = MutableLiveData<Resource<T>>().apply {
        value = resource
    } as LiveData<Resource<T>>
}
