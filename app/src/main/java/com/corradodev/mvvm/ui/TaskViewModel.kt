package com.corradodev.mvvm.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.corradodev.mvvm.data.RepositoryResponse
import com.corradodev.mvvm.data.Task
import com.corradodev.mvvm.data.TaskRepository
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/17/17.
 */

class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun getTask(id: Long): MutableLiveData<RepositoryResponse<Task>> {
        return taskRepository.find(id);
    }

    fun saveTask(task: Task) {
        taskRepository.save(task)
    }

    fun deleteTask(task: Task) {
        taskRepository.delete(task)
    }
}