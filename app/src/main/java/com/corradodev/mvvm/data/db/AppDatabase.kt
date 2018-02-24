package com.corradodev.mvvm.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.data.task.TaskDAO

@Database(entities = [(Task::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO
}