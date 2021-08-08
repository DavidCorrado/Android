package com.corradodev.mvvm.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.corradodev.mvvm.ui.task.TaskScreen
import com.corradodev.mvvm.ui.task.TaskViewModel
import com.corradodev.mvvm.ui.tasks.TasksScreen
import com.corradodev.mvvm.ui.theme.AppTheme

@Composable
fun MainApp() {
    val navController = rememberNavController()
    AppTheme {
        NavHost(navController = navController, startDestination = "tasks") {
            composable("tasks") {
                TasksScreen(hiltViewModel(), showTaskScreen = { taskId ->
                    navController.navigate("task/$taskId")
                }
                )
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
                TaskScreen(viewModel, taskId, deleteTask = {
                    navController.popBackStack()
                },
                    doneTask = {
                        navController.popBackStack()
                    })
            }
        }
    }
}