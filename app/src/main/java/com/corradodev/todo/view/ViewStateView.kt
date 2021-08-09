package com.corradodev.todo.view

import androidx.compose.runtime.Composable
import com.corradodev.todo.data.ViewState

@Composable
fun <T> ViewStateView(viewState: ViewState<T>, content: @Composable (T) -> Unit) {
    when (viewState) {
        is ViewState.Loading -> {
            LoadingView()
        }
        is ViewState.Error -> {
            ErrorView(viewState.throwable)
        }
        is ViewState.Success -> {
            if ((viewState.data as? List<*>)?.isEmpty() == true) {
                EmptyView()
            } else {
                content(viewState.data)
            }
        }
    }
}