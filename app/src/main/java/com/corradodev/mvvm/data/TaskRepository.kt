package com.corradodev.mvvm.data

import android.arch.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by davidcorrado on 11/15/17.
 */

@Singleton
class TaskRepository @Inject constructor(private val taskDatabaseRepository: TaskDatabaseRepository, private val taskNetworkRepository: TaskNetworkRepository) : RepositoryInterface<Task> {
    private var taskList: MutableLiveData<RepositoryResponse<List<Task>>> = MutableLiveData<RepositoryResponse<List<Task>>>()
    private var task: MutableLiveData<RepositoryResponse<Task>> = MutableLiveData<RepositoryResponse<Task>>()

    override fun find(id: Long): MutableLiveData<RepositoryResponse<Task>> {
        taskDatabaseRepository.find(id, object : FindResult<Task> {
            override fun findResult(findResult: RepositoryResponse<Task>) {
                findResult.data?.let { task.postValue(RepositoryResponse(it)) }
            }
        })
        taskNetworkRepository.find(id, object : FindResult<Task> {
            override fun findResult(findResult: RepositoryResponse<Task>) {
                findResult.data?.let { taskDatabaseRepository.save(it) }
            }
        })
        return task
    }

    override fun findAll(): MutableLiveData<RepositoryResponse<List<Task>>> {
        taskDatabaseRepository.findAll(object : FindAllResult<Task> {
            override fun findAllResults(findAllResult: RepositoryResponse<List<Task>>) {
                findAllResult.data?.let { taskList.postValue(RepositoryResponse(it)) }
            }
        })
        taskNetworkRepository.findAll(object : FindAllResult<Task> {
            override fun findAllResults(findAllResult: RepositoryResponse<List<Task>>) {
                findAllResult.data?.let {
                    for (task: Task in it) {
                        taskDatabaseRepository.save(task)
                    }
                }
            }
        })
        return taskList
    }

    override fun save(data: Task) {
        taskNetworkRepository.save(data, object : SaveResult<Task> {
            override fun saveResult(saveResult: RepositoryResponse<Task>) {
                taskDatabaseRepository.save(data)
            }
        })
    }

    override fun delete(data: Task) {
        taskNetworkRepository.delete(data, object : DeleteResult {
            override fun deleteResult(deleteResult: RepositoryResponse<Unit>) {
                taskDatabaseRepository.delete(data)
            }
        })
    }

    override fun deleteAll() {
        taskNetworkRepository.deleteAll(object : DeleteAllResult {
            override fun deleteAllResult(deleteAllResult: RepositoryResponse<Unit>) {
                taskDatabaseRepository.deleteAll()
            }
        })
    }
}