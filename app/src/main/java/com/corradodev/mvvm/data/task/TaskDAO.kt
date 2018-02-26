package com.corradodev.mvvm.data.task

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE


@Dao
interface TaskDAO {

    @Query("select * from tasks")
    fun findAll(): LiveData<List<Task>>

    @Query("select * from tasks where id = :id")
    fun find(id: Long): LiveData<Task>

    @Insert(onConflict = REPLACE)
    fun save(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(tasks: List<Task>)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAll()
}