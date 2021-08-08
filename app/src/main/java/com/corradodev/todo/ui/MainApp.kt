package com.corradodev.todo.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.corradodev.todo.ui.task.TaskScreen
import com.corradodev.todo.ui.task.TaskViewModel
import com.corradodev.todo.ui.tasks.TasksScreen
import com.corradodev.todo.ui.theme.AppTheme

@Composable
fun MainApp() {
    val navController = rememberNavController()
    AppTheme {
        NavHost(navController = navController, startDestination = "tasks") {
            composable("tasks") {
                TasksScreen(hiltViewModel(), navController)
            }
            composable(
                "task/{taskId}",
                arguments = listOf(navArgument("taskId") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                val viewModel = hiltViewModel<TaskViewModel>()
                val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0
                viewModel.loadTask(taskId)
                TaskScreen(viewModel, taskId, navController = navController)
            }
        }
    }
}