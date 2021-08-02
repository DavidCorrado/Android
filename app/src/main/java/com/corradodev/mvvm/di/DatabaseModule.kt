package com.corradodev.mvvm.di

import android.content.Context
import androidx.room.Room
import com.corradodev.mvvm.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "app-db").build()

    @Provides
    fun providesTaskDAO(database: AppDatabase) = database.taskDAO()
}