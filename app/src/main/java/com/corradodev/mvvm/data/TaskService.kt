package com.corradodev.mvvm.data

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by davidcorrado on 2/10/18.
 */

interface TaskService {
    @GET("tasks/{id}")
    fun findTask(@Path("id") id: Long): Call<Task>

    @GET("tasks")
    fun findTasks(): Call<List<Task>>

    @POST("tasks")
    fun saveTask(@Body task: Task): Call<RequestBody>

    @DELETE("tasks/{id}")
    fun deleteTask(@Path("id") id: Long): Call<RequestBody>

    @DELETE("tasks")
    fun deleteTasks(): Call<RequestBody>
}