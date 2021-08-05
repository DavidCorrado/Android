package com.corradodev.mvvm.extension

import com.corradodev.mvvm.data.Result
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

fun <T> Flow<StoreResponse<T>>.toResult() = mapNotNull { storeResponse ->
    when (storeResponse) {
        is StoreResponse.Loading -> {
            Result.Loading
        }
        is StoreResponse.Data -> {
            Result.Success(storeResponse.value)
        }
        is StoreResponse.Error.Message -> {
            Result.Error(Exception(storeResponse.message))
        }
        is StoreResponse.Error.Exception -> {
            Result.Error(storeResponse.error)
        }
        else -> {
            null
        }
    }
}