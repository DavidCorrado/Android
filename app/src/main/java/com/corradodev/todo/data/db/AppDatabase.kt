package com.corradodev.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.corradodev.todo.data.task.Task
import com.corradodev.todo.data.task.TaskDAO

@Database(entities = [(Task::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO
}
