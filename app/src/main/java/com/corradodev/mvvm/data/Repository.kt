package com.corradodev.mvvm.data

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    fun find(id: Long): Flow<Result<T>>
    fun findAll(): Flow<Result<List<T>>>
    suspend fun save(data: T): Result<Unit>
    suspend fun delete(data: T): Result<Unit>
    suspend fun deleteAll(): Result<Unit>
}