package com.corradodev.mvvm.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.corradodev.mvvm.data.api.APIResponse

abstract class FetchResource<RequestType> @MainThread
internal constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<RequestType>>()

    fun start(): LiveData<Resource<RequestType>> {
        result.value = Resource.loading(null)
        val dbSource = databaseLoad()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                networkFetch(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
        return result
    }

    private fun networkFetch(dbSource: LiveData<RequestType>) {
        val apiResponse = networkCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource<APIResponse<RequestType>>(apiResponse) { response ->
            result.removeSource<APIResponse<RequestType>>(apiResponse)
            result.removeSource(dbSource)
            if (response?.isSuccessful == true) {
                appExecutors.diskIO().execute({
                    response.body?.let {
                        databaseSave(it)
                    }

                    appExecutors.mainThread().execute({
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(databaseLoad()
                        ) { newData -> result.setValue(Resource.success(newData)) }
                    })
                })
            } else {
                result.addSource(dbSource
                ) { newData -> result.setValue(Resource.error(response?.errorMessage, newData)) }
            }
        }
    }

    @WorkerThread
    protected abstract fun databaseSave(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: RequestType?): Boolean

    @MainThread
    protected abstract fun databaseLoad(): LiveData<RequestType>

    @MainThread
    protected abstract fun networkCall(): LiveData<APIResponse<RequestType>>
}