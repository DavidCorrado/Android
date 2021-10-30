package com.corradodev.todo.extension

import com.corradodev.todo.data.DataError
import com.corradodev.todo.data.DataState
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

fun <T> Flow<StoreResponse<T>>.toResult() = mapNotNull { storeResponse ->
    when (storeResponse) {
        is StoreResponse.Loading -> {
            DataState.Loading
        }
        is StoreResponse.Data -> {
            DataState.Success(storeResponse.value)
        }
        is StoreResponse.Error.Message -> {
            DataState.Error(DataError(storeResponse.message))
        }
        is StoreResponse.Error.Exception -> {
            DataState.Error(DataError(storeResponse.error.message))
        }
        else -> {
            null
        }
    }
}
