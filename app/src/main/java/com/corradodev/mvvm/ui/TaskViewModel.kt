package com.corradodev.mvvm.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.corradodev.mvvm.data.RepositoryListener
import com.corradodev.mvvm.data.Resource
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.data.task.TaskRepository
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/17/17.
 */

class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun getTask(id: Long): LiveData<Resource<Task>> {
        return taskRepository.find(id);
    }

    fun saveTask(task: Task, repositoryListener: RepositoryListener) {
        taskRepository.save(task, repositoryListener)
    }

    fun deleteTask(task: Task, repositoryListener: RepositoryListener) {
        taskRepository.delete(task, repositoryListener)
    }
}