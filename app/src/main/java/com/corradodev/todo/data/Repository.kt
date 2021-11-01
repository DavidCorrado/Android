package com.corradodev.todo.data

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    fun find(id: Long): Flow<DataState<T>>
    fun findAll(): Flow<DataState<List<T>>>
    suspend fun save(data: T): DataState<Unit>
    suspend fun delete(data: T): DataState<Unit>
}
