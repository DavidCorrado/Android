package com.corradodev.mvvm.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.corradodev.mvvm.R
import com.corradodev.mvvm.data.Task
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_task_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.cancelButton
import javax.inject.Inject

fun Context.taskEditIntent(task: Task?): Intent {
    return Intent(this, TaskEditActivity::class.java).apply {
        if (task != null) {
            putExtra(INTENT_TASK_ID, task.id)
        }
    }
}

private const val INTENT_TASK_ID = "INTENT_TASK_ID"
private const val INVALID_TASK_ID = 0L

class TaskEditActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var tasksViewModel: TasksViewModel
    private var id: Long = INVALID_TASK_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasksViewModel = ViewModelProviders.of(this, viewModelFactory).get(TasksViewModel::class.java)
        setContentView(R.layout.activity_task_edit)

        id = intent.getLongExtra(INTENT_TASK_ID, INVALID_TASK_ID)
        if (savedInstanceState == null && id != INVALID_TASK_ID) {
            tasksViewModel.getTask(id).observe(this, Observer<Task> { it ->
                it?.let {
                    et_title.setText(it.name)
                    et_description.setText(it.detail)
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
        val task = Task(id, et_title.text.toString(), et_description.text.toString())
        when (item.itemId) {
            R.id.action_done -> {
                tasksViewModel.saveTask(task)
                finish()
                return true
            }
            R.id.action_delete -> {
                alert(Appcompat, getString(R.string.delete_task_question)) {
                    positiveButton(R.string.delete) {
                        tasksViewModel.deleteTask(task)
                        finish()
                    }
                    cancelButton { }
                }.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
