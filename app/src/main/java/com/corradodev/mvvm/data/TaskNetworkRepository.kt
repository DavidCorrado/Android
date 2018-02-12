package com.corradodev.mvvm.data

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


/**
 * Created by davidcorrado on 11/15/17.
 */

class TaskNetworkRepository @Inject constructor(private val taskService: TaskService) : RepositoryDatasourceInterface<Task> {
    override fun find(id: Long, findResult: FindResult<Task>?) {
        taskService.findTask(id).enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>?, response: Response<Task>) {
                if (response.isSuccessful) {
                    findResult?.findResult(RepositoryResponse(response.body(), StatusType.SUCCESS, ""))
                } else {
                    findResult?.findResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
                }
            }

            override fun onFailure(call: Call<Task>?, t: Throwable?) {
                findResult?.findResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
            }
        })
    }

    override fun findAll(findAllResult: FindAllResult<Task>?) {
        taskService.findTasks().enqueue(object : Callback<List<Task>> {
            override fun onFailure(call: Call<List<Task>>?, t: Throwable?) {
                findAllResult?.findAllResults(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
            }

            override fun onResponse(call: Call<List<Task>>?, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    findAllResult?.findAllResults(RepositoryResponse(response.body(), StatusType.SUCCESS, ""))
                } else {
                    findAllResult?.findAllResults(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
                }
            }

        })
    }

    override fun save(data: Task, saveResult: SaveResult<Task>?) {
        taskService.saveTask(data).enqueue(object : Callback<RequestBody> {
            override fun onFailure(call: Call<RequestBody>?, t: Throwable?) {
                saveResult?.saveResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
            }

            override fun onResponse(call: Call<RequestBody>?, response: Response<RequestBody>) {
                if (response.isSuccessful) {
                    saveResult?.saveResult(RepositoryResponse(null, StatusType.SUCCESS, ""))
                } else {
                    saveResult?.saveResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
                }
            }

        })
    }

    override fun delete(data: Task, deleteResult: DeleteResult?) {
        taskService.deleteTask(data.id).enqueue(object : Callback<RequestBody> {
            override fun onFailure(call: Call<RequestBody>?, t: Throwable?) {
                deleteResult?.deleteResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
            }

            override fun onResponse(call: Call<RequestBody>?, response: Response<RequestBody>) {
                if (response.isSuccessful) {
                    deleteResult?.deleteResult(RepositoryResponse(null, StatusType.SUCCESS, ""))
                } else {
                    deleteResult?.deleteResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
                }
            }

        })
    }

    override fun deleteAll(deleteAllResult: DeleteAllResult?) {
        taskService.deleteTasks().enqueue(object : Callback<RequestBody> {
            override fun onFailure(call: Call<RequestBody>?, t: Throwable?) {
                deleteAllResult?.deleteAllResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
            }

            override fun onResponse(call: Call<RequestBody>?, response: Response<RequestBody>) {
                if (response.isSuccessful) {
                    deleteAllResult?.deleteAllResult(RepositoryResponse(null, StatusType.SUCCESS, ""))
                } else {
                    deleteAllResult?.deleteAllResult(RepositoryResponse(null, StatusType.GENERIC_ERROR, "Unknown error"))
                }
            }

        })
    }

}