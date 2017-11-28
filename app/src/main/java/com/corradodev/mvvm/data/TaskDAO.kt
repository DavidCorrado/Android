package com.corradodev.mvvm.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by davidcorrado on 11/16/17.
 */

@Dao
interface TaskDAO {

    @Query("select * from tasks")
    fun findAll(): LiveData<List<Task>>

    @Query("select * from tasks where id = :id")
    fun find(id: Long): LiveData<Task>

    @Insert(onConflict = REPLACE)
    fun save(task: Task)

    @Delete
    fun delete(task: Task)
}