package com.corradodev.mvvm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.corradodev.mvvm.data.api.APIResponse

abstract class FetchResource<ResultType> @MainThread
internal constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    fun start(): LiveData<Resource<ResultType>> {
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

    private fun networkFetch(dbSource: LiveData<ResultType>) {
        val apiResponse = networkCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource<APIResponse<ResultType>>(apiResponse) { response ->
            result.removeSource<APIResponse<ResultType>>(apiResponse)
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
    protected abstract fun databaseSave(item: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun databaseLoad(): LiveData<ResultType>

    @MainThread
    protected abstract fun networkCall(): LiveData<APIResponse<ResultType>>
}