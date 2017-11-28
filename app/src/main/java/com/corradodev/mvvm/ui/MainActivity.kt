package com.corradodev.mvvm.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.corradodev.mvvm.App
import com.corradodev.mvvm.R
import com.corradodev.mvvm.data.Task
import com.corradodev.mvvm.data.TaskRepository
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var tasksViewModel: TasksViewModel
    @Inject lateinit var taskRepository: TaskRepository
    var tasks: LiveData<List<Task>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel::class.java)
        App.appComponent.inject(tasksViewModel)
        tasksViewModel.initializeRepo()

        tasksViewModel.tasks!!.observeForever {
            it?.let {
                for (task: Task in it) {
                    var a = 0
                }
            }
        };
    }
}
