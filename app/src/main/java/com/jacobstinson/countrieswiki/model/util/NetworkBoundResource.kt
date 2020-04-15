package com.jacobstinson.countrieswiki.model.util

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType?>>()

    init {
        result.value = Resource.loading(null)

        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)

            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }



    /*************
    * Main Methods
    *************/
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) {
            result.setValue(Resource.loading(null))
        }

        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            response?.apply {
                if (status == Status.SUCCESS) {
                    saveResponseAndLoadFromDb(this)
                } else {
                    result.addSource(dbSource) {
                        result.setValue(Resource.error(null))
                    }
                }
            }
        }
    }

    private fun saveResponseAndLoadFromDb(response: Resource<RequestType>) {
        appExecutors.diskIO().execute {
            processResponse(response)?.let { requestType ->
                saveCallResult(requestType)
            }
            appExecutors.mainThread().execute {
                /* we specially request a new live data,
                otherwise we will get immediately last cached value,
                which may not be updated with latest results received from network. */
                result.addSource(loadFromDb()) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>



    /*************
    * Util Methods
    *************/
    @MainThread
    private fun setValue(newValue: Resource<ResultType?>) {
        if (result.value != newValue) result.value = newValue
    }

    fun asLiveData(): LiveData<Resource<ResultType?>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: Resource<RequestType>): RequestType? {
        return response.data
    }
}