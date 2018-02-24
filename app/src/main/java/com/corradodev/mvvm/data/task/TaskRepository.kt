package com.corradodev.mvvm.data.task

import android.arch.lifecycle.LiveData
import com.corradodev.mvvm.data.*
import com.corradodev.mvvm.data.api.APIResponse
import com.corradodev.mvvm.data.api.APIService
import kotlinx.coroutines.experimental.async
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDAO: TaskDAO, private val apiService: APIService, private val appExecutors: AppExecutors) : Repository<Task> {

    override fun find(id: Long): LiveData<Resource<Task>> {
        return object : FetchResource<Task, Task>(appExecutors) {
            override fun saveCallResult(item: Task) {
                taskDAO.save(item)
            }

            override fun shouldFetch(data: Task?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Task> {
                return taskDAO.find(id)
            }

            override fun createCall(): LiveData<APIResponse<Task>> {
                return apiService.findTask(id)
            }

        }.asLiveData()
    }

    override fun findAll(): LiveData<Resource<List<Task>>> {
        return object : FetchResource<List<Task>, List<Task>>(appExecutors) {
            override fun saveCallResult(item: List<Task>) {
                item.let {
                    taskDAO.saveAll(it)
                }
            }

            override fun shouldFetch(data: List<Task>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Task>> {
                return taskDAO.findAll()
            }

            override fun createCall(): LiveData<APIResponse<List<Task>>> {
                return apiService.findTasks()
            }

        }.asLiveData()
    }

    override fun save(data: Task, responseListener: RepositoryListener) {
        async {
            val response = APIResponse(apiService.saveTask(data).execute())
            if (response.isSuccessful && response.body != null) {
                taskDAO.save(response.body)
                responseListener.response(Resource.success(null))
            } else {
                responseListener.response(Resource.error(response.errorMessage, null))
            }
        }

    }

    override fun delete(data: Task, responseListener: RepositoryListener) {
        object : DeleteResource(appExecutors, responseListener) {
            override fun deleteCallResult() {
                taskDAO.delete(data)
            }

            override fun createCall(): Call<ResponseBody> {
                return apiService.deleteTask(data.id)
            }
        }
    }

    override fun deleteAll(responseListener: RepositoryListener) {
        object : DeleteResource(appExecutors, responseListener) {
            override fun deleteCallResult() {
                taskDAO.deleteAll()
            }

            override fun createCall(): Call<ResponseBody> {
                return apiService.deleteTasks()
            }
        }
    }
}