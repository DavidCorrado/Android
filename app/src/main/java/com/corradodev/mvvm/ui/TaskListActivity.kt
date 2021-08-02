package com.corradodev.mvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.corradodev.mvvm.data.Resource
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.databinding.ActivityTaskListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListActivity : AppCompatActivity() {
    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var binding: ActivityTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasksViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)

        tasksViewModel.getTasks().observe(this, Observer<Resource<List<Task>>> {
            it?.data?.let {
                binding.recyclerView.adapter = TaskAdapter(it) {
                    startActivity(TaskEditActivity.newInstance(this, it))
                }
            }
        })

        binding.fabAdd.setOnClickListener {
            startActivity(TaskEditActivity.newInstance(this, null))
        }
    }
}
