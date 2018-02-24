package com.corradodev.mvvm.di

import android.arch.persistence.room.Room
import android.content.Context
import com.corradodev.mvvm.data.AppExecutors
import com.corradodev.mvvm.data.api.APIService
import com.corradodev.mvvm.data.api.LiveDataCallAdapterFactory
import com.corradodev.mvvm.data.db.AppDatabase
import com.corradodev.mvvm.data.task.TaskDAO
import com.corradodev.mvvm.data.task.TaskRepository
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

    @Provides
    fun providesAppExecutors() = AppExecutors()

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()

    @Provides
    fun providesTaskDAO(database: AppDatabase) = database.taskDAO()

    @Provides
    fun providesTaskRepo(taskDAO: TaskDAO, apiService: APIService, appExecutors: AppExecutors) = TaskRepository(taskDAO, apiService,appExecutors)

    @Singleton
    @Provides
    fun providesRetrofit(): APIService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)  // <-- this is the important line!

        val retrofit = Retrofit.Builder()
                .baseUrl("https://davidcorrado-todo.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(httpClient.build())
                .build()
        return retrofit.create(APIService::class.java)
    }
}