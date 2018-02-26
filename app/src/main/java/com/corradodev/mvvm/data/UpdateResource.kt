package com.corradodev.mvvm.data

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class UpdateResource<ResultType> @MainThread
internal constructor(private val appExecutors: AppExecutors, private val responseListener: RepositoryListener) {
    fun start() {
        val apiResponse = networkUpdate()
        apiResponse.enqueue(object : Callback<ResultType> {
            override fun onFailure(call: Call<ResultType>?, t: Throwable?) {
                responseListener.response(Resource.error(null, null))
            }

            override fun onResponse(call: Call<ResultType>?, response: Response<ResultType>?) {
                appExecutors.diskIO().execute {
                    response?.body()?.let {
                        databaseUpdate(it)
                    }
                }
                responseListener.response(Resource.success(null))
            }

        })
    }

    @WorkerThread
    protected abstract fun databaseUpdate(item: ResultType)

    @MainThread
    protected abstract fun networkUpdate(): Call<ResultType>
}