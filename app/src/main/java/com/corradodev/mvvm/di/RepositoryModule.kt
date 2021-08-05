package com.corradodev.mvvm.di

import com.corradodev.mvvm.data.api.APIService
import com.corradodev.mvvm.data.db.AppDatabase
import com.corradodev.mvvm.data.task.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun providesTaskRepo(db: AppDatabase, apiService: APIService) =
        TaskRepository(db, apiService)
}