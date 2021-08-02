package com.corradodev.mvvm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.corradodev.mvvm.R
import com.corradodev.mvvm.data.RepositoryListener
import com.corradodev.mvvm.data.Resource
import com.corradodev.mvvm.data.ResourceStatus
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.databinding.ActivityTaskEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskEditActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private var id: Long = INVALID_TASK_ID
    private lateinit var binding: ActivityTaskEditBinding

    companion object {
        fun newInstance(context: Context, task: Task?): Intent {
            return Intent(context, TaskEditActivity::class.java).apply {
                if (task != null) {
                    putExtra(INTENT_TASK_ID, task.id)
                }
            }
        }

        private const val INTENT_TASK_ID = "INTENT_TASK_ID"
        private const val INVALID_TASK_ID = 0L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        binding = ActivityTaskEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getLongExtra(INTENT_TASK_ID, INVALID_TASK_ID)
        if (savedInstanceState == null && id != INVALID_TASK_ID) {
            taskViewModel.getTask(id).observe(this, Observer<Resource<Task>> {
                it?.data?.let {
                    binding.etTitle.setText(it.name)
                    binding.etDescription.setText(it.detail)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        if (id == INVALID_TASK_ID) {
            menu.removeItem(R.id.action_delete)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val task = Task(id, binding.etTitle.text.toString(), binding.etDescription.text.toString())
        when (item.itemId) {
            R.id.action_done -> {
                taskViewModel.saveTask(task, object : RepositoryListener {
                    override fun response(response: Resource<Unit>) {
                        if (response.status == ResourceStatus.SUCCESS) {
                            finish()
                        } else {
                            AlertDialog.Builder(this@TaskEditActivity).setMessage(response.message)
                                .show()
                        }
                    }

                })
                return true
            }
            R.id.action_delete -> {
                AlertDialog.Builder(this).setMessage(R.string.delete_task_question)
                    .setPositiveButton(
                        R.string.delete
                    ) { _, _ ->
                        taskViewModel.deleteTask(task, object : RepositoryListener {
                            override fun response(response: Resource<Unit>) {
                                if (response.status == ResourceStatus.SUCCESS) {
                                    finish()
                                } else {
                                    AlertDialog.Builder(this@TaskEditActivity)
                                        .setMessage(response.message).show()
                                }
                            }

                        })
                    }.setNegativeButton(R.string.cancel) { _, _ -> }
                    .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
