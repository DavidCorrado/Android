package com.corradodev.mvvm.ui.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.corradodev.mvvm.data.Result

@Composable
fun TasksScreen(viewModel: TasksViewModel, showTaskScreen: (taskId: Long) -> Unit) {
    val viewState by viewModel.state.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text("TopAppBar") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showTaskScreen(0) }) {
                Text("X")
            }
        },
        content = {
            viewState.let { viewState ->
                when (viewState) {
                    is Result.Success -> {
                        LazyColumn {
                            items(viewState.data) { task ->
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { showTaskScreen(task.id) }) {
                                    Text(
                                        text = task.name,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = task.detail,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
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