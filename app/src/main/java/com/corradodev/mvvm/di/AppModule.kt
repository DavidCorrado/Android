package com.corradodev.mvvm.di

import android.arch.persistence.room.Room
import android.content.Context
import com.corradodev.mvvm.data.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun providesTaskRepo(taskDatabaseRepository: TaskDatabaseRepository, taskNetworkRepository: TaskNetworkRepository) = TaskRepository(taskDatabaseRepository, taskNetworkRepository)

    @Provides
    fun providesTaskNetworkRepo(taskService: TaskService) = TaskNetworkRepository(taskService)

    @Singleton
    @Provides
    fun providesRetrofit(): TaskService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)  // <-- this is the important line!

        val retrofit = Retrofit.Builder()
                .baseUrl("https://davidcorrado-todo.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        return retrofit.create(TaskService::class.java)
    }
}