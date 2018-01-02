package com.corradodev.mvvm.ui

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
import javax.inject.Inject

fun Context.taskEditIntent(task: Task?): Intent {
    return Intent(this, TaskEditActivity::class.java).apply {
        if (task != null) {
            putExtra(INTENT_TASK_ID, task.id)
        }
    }
}

private const val INTENT_TASK_ID = "INTENT_TASK_ID"
private const val INVALID_TASK_ID = -1L

class TaskEditActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var tasksViewModel: TasksViewModel
    private var id = INVALID_TASK_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasksViewModel = ViewModelProviders.of(this, viewModelFactory).get(TasksViewModel::class.java)
        setContentView(R.layout.activity_task_edit)

        id = intent.getLongExtra(INTENT_TASK_ID, INVALID_TASK_ID)
//        if (id != INVALID_TASK_ID) {
//            //TODO replace forever with regular observe
//            tasksViewModel.getTask(id).observeForever {
//                it?.let {
//                    it.
//                }
//            };
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                tasksViewModel.saveTask(Task(et_title.text.toString(), et_description.text.toString()))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
