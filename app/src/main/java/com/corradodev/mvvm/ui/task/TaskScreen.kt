package com.corradodev.mvvm.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corradodev.mvvm.data.Result
import com.corradodev.mvvm.data.task.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    taskId: Long,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    deleteTask: (task: Task) -> Unit,
    doneTask: (task: Task) -> Unit
) {
    val viewState by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TopAppBar2") }, actions = {
                viewState.let { viewState ->
                    if (viewState is Result.Success) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                val result = viewModel.saveTask(viewState.data)
                                if (result is Result.Success) {
                                    doneTask(viewState.data)
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Done, contentDescription = "Done")
                        }
                        if (taskId != 0L) {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    val result = viewModel.deleteTask(viewState.data)
                                    if (result is Result.Success) {
                                        deleteTask(viewState.data)
                                    }
                                }
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            })
        },
        content = {
            viewState.let { viewState ->
                when (viewState) {
                    is Result.Success -> {
                        Column(modifier = Modifier.padding(8.dp)) {
                            TextField(
                                value = viewState.data.name,
                                onValueChange = { viewModel.taskModelUpdate(viewState.data.copy(name = it)) },
                                label = { Text("Name") }
                            )
                            TextField(
                                value = viewState.data.detail,
                                onValueChange = {
                                    viewModel.taskModelUpdate(
                                        viewState.data.copy(
                                            detail = it
                                        )
                                    )
                                },
                                label = { Text("Detail") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    is Result.Error -> {
                        Text("Error")
                    }
                    is Result.Loading -> {
                        Text("Loading")
                    }
                }
            }
        },
    )
}