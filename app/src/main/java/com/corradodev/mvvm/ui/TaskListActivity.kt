package com.corradodev.mvvm.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.corradodev.mvvm.R
import com.corradodev.mvvm.data.Resource
import com.corradodev.mvvm.data.task.Task
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_task_list.*
import javax.inject.Inject


class TaskListActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var tasksViewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasksViewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)
        setContentView(R.layout.activity_task_list)

        recycler_view.layoutManager =
            LinearLayoutManager(this)

        tasksViewModel.getTasks().observe(this, Observer<Resource<List<Task>>> {
            it?.data?.let {
                recycler_view.adapter = TaskAdapter(it) {
                    startActivity(TaskEditActivity.newInstance(this, it))
                }
            }
        })

        fab_add.setOnClickListener {
            startActivity(TaskEditActivity.newInstance(this, null))
        }
    }
}
