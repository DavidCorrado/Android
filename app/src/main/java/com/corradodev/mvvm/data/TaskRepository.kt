package com.corradodev.mvvm.data

import android.arch.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by davidcorrado on 11/15/17.
 */

@Singleton
class TaskRepository @Inject constructor(val taskRepository: TaskDatabaseRepository) : RepositoryInterface<Task> {
    override fun find(id: Long): LiveData<Task> {
        return taskRepository.find(id)
    }

    override fun findAll(): LiveData<List<Task>> {
        return taskRepository.findAll()
    }

    override fun save(t: Task) {
        taskRepository.save(t)
    }

    override fun delete(t: Task) {
        taskRepository.delete(t)
    }
}