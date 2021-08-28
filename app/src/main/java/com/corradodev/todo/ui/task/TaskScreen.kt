package com.corradodev.todo.ui.task

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.corradodev.todo.R
import com.corradodev.todo.data.ViewState
import com.corradodev.todo.data.successData
import com.corradodev.todo.view.ViewStateView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    taskId: Long,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavController
) {
    val viewState by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.to_do_details)) },
                actions = {
                    viewState.successData?.let { task ->
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    val result = viewModel.saveTask(task)
                                    if (result is ViewState.Success) {
                                        navController.popBackStack()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = stringResource(id = R.string.content_desc_done),
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                        if (taskId != 0L) {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val result = viewModel.deleteTask(task)
                                        if (result is ViewState.Success) {
                                            navController.popBackStack()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = stringResource(id = R.string.content_desc_delete),
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.content_desc_back)
                        )
                    }
                },
            )
        },
        content = {
            ViewStateView(viewState) { task ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = task.name,
                        onValueChange = { viewModel.taskModelUpdate(task.copy(name = it)) },
                        label = { Text(stringResource(id = R.string.name)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = task.detail,
                        onValueChange = {
                            viewModel.taskModelUpdate(
                                task.copy(
                                    detail = it
                                )
                            )
                        },
                        label = { Text(stringResource(id = R.string.detail)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
    )
}
