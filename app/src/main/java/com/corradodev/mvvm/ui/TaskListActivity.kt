package com.corradodev.mvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.corradodev.mvvm.data.Result
import com.corradodev.mvvm.databinding.ActivityTaskListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tasksViewModel.uiState.collect { result ->
                    if (result is Result.Success) {
                        binding.recyclerView.adapter = TaskAdapter(result.data) {
                            startActivity(TaskEditActivity.newInstance(this@TaskListActivity, it))
                        }
                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            startActivity(TaskEditActivity.newInstance(this, null))
        }
    }
}
