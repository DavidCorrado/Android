package com.corradodev.mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.corradodev.mvvm.data.RepositoryListener
import com.corradodev.mvvm.data.Resource
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.data.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun getTask(id: Long): LiveData<Resource<Task>> {
        return taskRepository.find(id)
    }

    fun saveTask(task: Task, repositoryListener: RepositoryListener) {
        taskRepository.save(task, repositoryListener)
    }

    fun deleteTask(task: Task, repositoryListener: RepositoryListener) {
        taskRepository.delete(task, repositoryListener)
    }
}