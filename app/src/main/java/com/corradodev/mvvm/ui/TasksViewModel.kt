package com.corradodev.mvvm.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.corradodev.mvvm.data.Task
import com.corradodev.mvvm.data.TaskRepository
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/17/17.
 */

class TasksViewModel : ViewModel() {
    var tasks: LiveData<List<Task>>? = null
    @Inject lateinit var taskRepository: TaskRepository

    fun initializeRepo() {
        if (tasks != null) {
            return;//tasks already created
        }
        taskRepository.save(Task("Title", "Description"))
        tasks = taskRepository.findAll()
    }
}