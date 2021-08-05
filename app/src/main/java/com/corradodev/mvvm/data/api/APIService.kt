package com.corradodev.mvvm.data.api

import com.corradodev.mvvm.data.task.Task
import retrofit2.http.*

interface APIService {
    @GET("tasks/{id}")
    suspend fun findTask(@Path("id") id: Long): Task

    @GET("tasks")
    suspend fun findTasks(): List<Task>

    @POST("tasks")
    suspend fun saveTask(@Body task: Task): Task

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Long)

    @DELETE("tasks")
    suspend fun deleteTasks()
}