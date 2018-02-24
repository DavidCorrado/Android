package com.corradodev.mvvm.data

class Resource<out T>(val status: ResourceStatus, val data: T?, val message: String) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, "")
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, msg ?: "Unknown server error")
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(ResourceStatus.LOADING, data, "")
        }
    }
}