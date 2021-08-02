package com.corradodev.mvvm.di

import com.corradodev.mvvm.data.AppExecutors
import com.corradodev.mvvm.data.api.APIService
import com.corradodev.mvvm.data.task.TaskDAO
import com.corradodev.mvvm.data.task.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesAppExecutors() = AppExecutors()

    @Provides
    fun providesTaskRepo(taskDAO: TaskDAO, apiService: APIService, appExecutors: AppExecutors) =
        TaskRepository(taskDAO, apiService, appExecutors)
}