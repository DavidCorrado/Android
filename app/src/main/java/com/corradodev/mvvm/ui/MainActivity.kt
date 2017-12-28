package com.corradodev.mvvm.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.corradodev.mvvm.R
import com.corradodev.mvvm.data.Task
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var tasksViewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasksViewModel = ViewModelProviders.of(this, viewModelFactory).get(TasksViewModel::class.java)
        setContentView(R.layout.activity_main)

        //TODO replace forever with regular observe
        tasksViewModel.tasks.observeForever {
            it?.let {
                for (task: Task in it) {
                }
            }
        };
    }
}
