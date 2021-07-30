package com.corradodev.mvvm.data.task

import androidx.lifecycle.LiveData
import com.corradodev.mvvm.data.*
import com.corradodev.mvvm.data.api.APIResponse
import com.corradodev.mvvm.data.api.APIResponseAdapter
import com.corradodev.mvvm.data.api.APIService
import com.corradodev.mvvm.data.api.MyCall
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDAO: TaskDAO, private val apiService: APIService, private val appExecutors: AppExecutors) : Repository<Task> {

    override fun find(id: Long): LiveData<Resource<Task>> {
        return object : FetchResource<Task>(appExecutors) {
            override fun databaseSave(item: Task) {
                taskDAO.save(item)
            }

            override fun shouldFetch(data: Task?): Boolean {
                return true
            }

            override fun databaseLoad(): LiveData<Task> {
                return taskDAO.find(id)
            }

            override fun networkCall(): LiveData<APIResponse<Task>> {
                return apiService.findTask(id)
            }
        }.start()
    }

    override fun findAll(): LiveData<Resource<List<Task>>> {
        return object : FetchResource<List<Task>>(appExecutors) {
            override fun databaseSave(item: List<Task>) {
                taskDAO.saveAll(item)
            }

            override fun shouldFetch(data: List<Task>?): Boolean {
                return true
            }

            override fun databaseLoad(): LiveData<List<Task>> {
                return taskDAO.findAll()
            }

            override fun networkCall(): LiveData<APIResponse<List<Task>>> {
                return apiService.findTasks()
            }
        }.start()
    }

    override fun save(data: Task, repositoryListener: RepositoryListener) {
        object : UpdateResource<Task>(appExecutors, repositoryListener) {
            override fun databaseUpdate(item: Task) {
                taskDAO.save(item)
            }

            override fun networkUpdate(): MyCall<Task> {
                return apiService.saveTask(data)
            }
        }.start()
    }

    override fun delete(data: Task, repositoryListener: RepositoryListener) {
        object : UpdateResource<ResponseBody>(appExecutors, repositoryListener) {
            override fun databaseUpdate(item: ResponseBody) {
                taskDAO.delete(data)
            }

            override fun networkUpdate(): MyCall<ResponseBody> {
                return apiService.deleteTask(data.id)
            }
        }.start()
    }

    override fun deleteAll(repositoryListener: RepositoryListener) {
        object : UpdateResource<ResponseBody>(appExecutors, repositoryListener) {
            override fun databaseUpdate(item: ResponseBody) {
                taskDAO.deleteAll()
            }

            override fun networkUpdate(): MyCall<ResponseBody> {
                return apiService.deleteTasks()
            }
        }.start()
    }
}