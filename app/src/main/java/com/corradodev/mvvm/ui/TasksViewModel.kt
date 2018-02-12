package com.corradodev.mvvm.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.corradodev.mvvm.data.RepositoryResponse
import com.corradodev.mvvm.data.Task
import com.corradodev.mvvm.data.TaskRepository
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/17/17.
 */

class TasksViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun getTasks(): MutableLiveData<RepositoryResponse<List<Task>>> {
        return taskRepository.findAll();
    }
}