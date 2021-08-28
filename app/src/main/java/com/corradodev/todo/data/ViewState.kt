package com.corradodev.todo.data

sealed class ViewState<out R> {
    data class Success<out T>(val data: T) : ViewState<T>()
    data class Error(val throwable: Throwable) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}

val <T> ViewState<T>.successData
    get() = (this as? ViewState.Success)?.data
