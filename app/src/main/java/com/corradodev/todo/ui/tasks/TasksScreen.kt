package com.corradodev.todo.ui.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.corradodev.todo.R
import com.corradodev.todo.view.DataStateView

@Composable
fun TasksScreen(viewModel: TasksViewModel, navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.app_name)) }) },
        content = {
            val viewState by viewModel.state.collectAsState()
            DataStateView(viewState) { tasks ->
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tasks) { task ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { navController.navigate("task/${task.id}") }
                        ) {
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
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("task/0") }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.content_desc_add)
                )
            }
        },
    )
}
