package com.corradodev.todo.data

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    fun find(id: Long): Flow<ViewState<T>>
    fun findAll(): Flow<ViewState<List<T>>>
    suspend fun save(data: T): ViewState<Unit>
    suspend fun delete(data: T): ViewState<Unit>
    suspend fun deleteAll(): ViewState<Unit>
}
