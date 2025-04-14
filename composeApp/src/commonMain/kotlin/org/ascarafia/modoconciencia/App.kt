package org.ascarafia.modoconciencia

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ascarafia.modoconciencia.ui.main_screen.MainScreen
import org.ascarafia.modoconciencia.ui.task_list.CreateTaskScreen
import org.ascarafia.modoconciencia.ui.task_list.TaskDetailScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ascarafia.modoconciencia.ui.task_list.TaskListScreen
import org.ascarafia.modoconciencia.ui.task_list.TaskListViewModel
import org.ascarafia.modoconciencia.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val taskViewModel: TaskListViewModel = koinViewModel<TaskListViewModel>()

    AppTheme {
        NavHost(navController = navController, startDestination = "mainScreen") {
            composable("mainScreen") {
                MainScreen(navController)
            }

            composable("taskDetail/{taskId}") { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")
                TaskDetailScreen(navController, taskId, taskViewModel)
            }

            composable("createTask") {
                CreateTaskScreen(navController, taskViewModel)
            }
        }
    }
}