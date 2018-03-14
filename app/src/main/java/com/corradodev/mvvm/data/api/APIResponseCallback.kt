package com.corradodev.mvvm.data.api

interface APIResponseCallback<T> {
    fun onResponse(response: APIResponse<T>)
}