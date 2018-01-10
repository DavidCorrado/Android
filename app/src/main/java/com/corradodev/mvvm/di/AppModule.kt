package com.corradodev.mvvm.di

import android.arch.persistence.room.Room
import android.content.Context
import com.corradodev.mvvm.data.AppDatabase
import com.corradodev.mvvm.data.TaskDAO
import com.corradodev.mvvm.data.TaskDatabaseRepository
import com.corradodev.mvvm.data.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by davidcorrado on 11/17/17.
 */

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()

    @Provides
    fun providesTaskDAO(database: AppDatabase) = database.taskDAO()

    @Provides
    fun providesTaskDatabaseRepo(taskDAO: TaskDAO) = TaskDatabaseRepository(taskDAO)

    @Provides
    fun providesTaskRepo(taskDatabaseRepository: TaskDatabaseRepository) = TaskRepository(taskDatabaseRepository)
}