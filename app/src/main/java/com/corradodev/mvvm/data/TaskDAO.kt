package com.corradodev.mvvm.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Created by davidcorrado on 11/16/17.
 */

@Dao
interface TaskDAO {

    @Query("select * from tasks")
    fun findAll(): Flowable<List<Task>>

    @Query("select * from tasks where id = :id")
    fun find(id: Long): Flowable<Task>

    @Insert(onConflict = REPLACE)
    fun save(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAll()
}