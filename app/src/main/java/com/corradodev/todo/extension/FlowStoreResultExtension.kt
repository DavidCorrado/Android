package com.corradodev.todo.extension

import com.corradodev.todo.data.ViewState
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

fun <T> Flow<StoreResponse<T>>.toResult() = mapNotNull { storeResponse ->
    when (storeResponse) {
        is StoreResponse.Loading -> {
            ViewState.Loading
        }
        is StoreResponse.Data -> {
            ViewState.Success(storeResponse.value)
        }
        is StoreResponse.Error.Message -> {
            ViewState.Error(Exception(storeResponse.message))
        }
        is StoreResponse.Error.Exception -> {
            ViewState.Error(storeResponse.error)
        }
        else -> {
            null
        }
    }
}
