package com.corradodev.mvvm.data

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.corradodev.mvvm.data.api.APIResponse
import com.corradodev.mvvm.data.api.APIResponseCallback
import com.corradodev.mvvm.data.api.MyCall

abstract class UpdateResource<ResultType> @MainThread
internal constructor(private val appExecutors: AppExecutors, private val responseListener: RepositoryListener) {
    fun start() {
        val apiResponse = networkUpdate()
        apiResponse.enqueue(object : APIResponseCallback<ResultType> {
            override fun onResponse(response: APIResponse<ResultType>) {
                if (response.isSuccessful) {
                    appExecutors.diskIO().execute {
                        response.body?.let {
                            databaseUpdate(it)
                        }
                    }
                    responseListener.response(Resource.success(null))
                } else {
                    responseListener.response(Resource.error(response.errorMessage, null))
                }
            }
        })
    }

    @WorkerThread
    protected abstract fun databaseUpdate(item: ResultType)

    @MainThread
    protected abstract fun networkUpdate(): MyCall<ResultType>
}