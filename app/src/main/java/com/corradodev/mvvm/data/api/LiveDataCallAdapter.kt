package com.corradodev.mvvm.data.api

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<APIResponse<T>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): LiveData<APIResponse<T>> {
        return object : LiveData<APIResponse<T>>() {
            internal var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<T> {
                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(APIResponse(response))
                        }

                        override fun onFailure(call: Call<T>, throwable: Throwable) {
                            postValue(APIResponse(throwable))
                        }
                    })
                }
            }
        }
    }
}