package com.corradodev.mvvm.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.corradodev.mvvm.data.Resource
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.data.task.TaskRepository
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/17/17.
 */

class TasksViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun getTasks(): LiveData<Resource<List<Task>>> {
        return taskRepository.findAll();
    }
}