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
    var tasks: LiveData<List<Task>> = taskRepository.findAll()
}