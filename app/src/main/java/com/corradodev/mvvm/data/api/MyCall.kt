package com.corradodev.mvvm.data.api

interface MyCall<T> {
    fun cancel()
    fun enqueue(callback: APIResponseCallback<T>)
    fun clone(): MyCall<T>
}