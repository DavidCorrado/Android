package com.corradodev.mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.corradodev.mvvm.data.Resource
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.data.task.TaskRepository
import javax.inject.Inject

class TasksViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun getTasks(): LiveData<Resource<List<Task>>> {
        return taskRepository.findAll()
    }
}