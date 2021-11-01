package com.corradodev.todo.data

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val error: DataError) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}

val <T> DataState<T>.successData
    get() = (this as? DataState.Success)?.data
