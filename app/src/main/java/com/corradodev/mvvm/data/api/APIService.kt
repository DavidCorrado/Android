package com.corradodev.mvvm.data.api

import android.arch.lifecycle.LiveData
import com.corradodev.mvvm.data.task.Task
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @GET("tasks/{id}")
    fun findTask(@Path("id") id: Long): LiveData<APIResponse<Task>>

    @GET("tasks")
    fun findTasks(): LiveData<APIResponse<List<Task>>>

    @POST("tasks")
    fun saveTask(@Body task: Task): Call<Task>

    @DELETE("tasks/{id}")
    fun deleteTask(@Path("id") id: Long): Call<ResponseBody>

    @DELETE("tasks")
    fun deleteTasks(): Call<ResponseBody>
}