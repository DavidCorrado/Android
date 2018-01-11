package com.corradodev.mvvm.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.corradodev.mvvm.data.Task
import com.corradodev.mvvm.data.TaskRepository
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/17/17.
 */

class TasksViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun getTasks(): LiveData<List<Task>> {
        return taskRepository.findAll();
    }

    fun getTask(id: Long): LiveData<Task> {
        return taskRepository.find(id);
    }

    fun saveTask(task: Task) {
        taskRepository.save(task)
    }

    fun deleteTask(task: Task) {
        taskRepository.delete(task)
    }
}