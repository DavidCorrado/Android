package com.corradodev.mvvm.data.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Query("select * from tasks")
    fun findAll(): Flow<List<Task>>

    @Query("select * from tasks where id = :id")
    fun find(id: Long): Flow<Task>

    @Insert(onConflict = REPLACE)
    suspend fun save(task: Task)

    @Insert(onConflict = REPLACE)
    suspend fun saveAll(tasks: List<Task>)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}