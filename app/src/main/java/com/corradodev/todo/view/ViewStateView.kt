package com.corradodev.todo.view

import androidx.compose.runtime.Composable
import com.corradodev.todo.data.DataState

@Composable
fun <T> DataStateView(dataState: DataState<T>, content: @Composable (T) -> Unit) {
    when (dataState) {
        is DataState.Loading -> {
            LoadingView()
        }
        is DataState.Error -> {
            ErrorView(dataState.error)
        }
        is DataState.Success -> {
            if ((dataState.data as? List<*>)?.isEmpty() == true) {
                EmptyView()
            } else {
                content(dataState.data)
            }
        }
    }
}
