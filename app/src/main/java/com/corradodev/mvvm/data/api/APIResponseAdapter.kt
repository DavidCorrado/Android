package com.corradodev.mvvm.data.api

import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


object APIResponseAdapter {
    class APIResponseAdapterFactory : CallAdapter.Factory() {
        override fun get(returnType: Type, annotations: Array<Annotation>,
                         retrofit: Retrofit): CallAdapter<*, *>? {
            if (CallAdapter.Factory.getRawType(returnType) != MyCall::class.java) {
                return null
            }
            if (returnType !is ParameterizedType) {
                throw IllegalStateException(
                        "MyCall must have generic type (e.g., MyCall<ResponseBody>)")
            }
            val responseType = CallAdapter.Factory.getParameterUpperBound(0, returnType)
            return APIResponseCallAdapter<Any>(responseType)
        }

        private class APIResponseCallAdapter<R> internal constructor(private val responseType: Type) : CallAdapter<R, MyCall<R>> {

            override fun responseType(): Type {
                return responseType
            }

            override fun adapt(call: Call<R>): MyCall<R> {
                return MyCallAdapter(call)
            }
        }
    }

    internal class MyCallAdapter<T>(private val call: Call<T>) : MyCall<T> {

        override fun cancel() {
            call.cancel()
        }

        override fun enqueue(callback: APIResponseCallback<T>) {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    callback.onResponse(APIResponse(response))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    callback.onResponse(APIResponse(t))
                }
            })
        }

        override fun clone(): MyCall<T> {
            return MyCallAdapter(call.clone())
        }
    }
}