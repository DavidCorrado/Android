package com.corradodev.mvvm.data

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class DeleteResource @MainThread
internal constructor(private val appExecutors: AppExecutors, private val responseListener: RepositoryListener) {
    init {
        updateFromNetwork()
    }

    private fun updateFromNetwork() {
        val apiResponse = createCall()
        apiResponse.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                responseListener.response(Resource.error(null, null))
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                appExecutors.diskIO().execute {
                    deleteCallResult()
                }
                responseListener.response(Resource.success(null))
            }

        })
    }

    @WorkerThread
    protected abstract fun deleteCallResult()

    @MainThread
    protected abstract fun createCall(): Call<ResponseBody>
}