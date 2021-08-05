package com.corradodev.mvvm.util

import com.corradodev.mvvm.data.Result

object ResultUtil {
    suspend fun getResult(
        api: (suspend () -> Unit),
        database: (suspend () -> Unit)
    ): Result<Unit> {
        return try {
            api.invoke()
            database.invoke()
            Result.Success(Unit)
        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }
}