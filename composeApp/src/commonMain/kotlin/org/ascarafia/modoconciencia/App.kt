package org.ascarafia.modoconciencia

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ascarafia.modoconciencia.ui.main_screen.MainScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.CreateLogScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.LogsListScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.LogDetailScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.LogListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ascarafia.modoconciencia.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val logsViewModel: LogListViewModel = koinViewModel<LogListViewModel>()

    AppTheme {
        NavHost(navController = navController, startDestination = "mainScreen") {
            composable("mainScreen") {
                MainScreenRoot(navController)
            }

            composable("bitacora") {
                LogsListScreenRoot(navController, logsViewModel)
            }

            composable("logDetail/{logId}") { backStackEntry ->
                val logId = backStackEntry.arguments?.getString("logId")
                LogDetailScreenRoot(navController, logId, logsViewModel)
            }

            composable("createLog/{logId}") { backStackEntry ->
                val logId = backStackEntry.arguments?.getString("logId")
                CreateLogScreenRoot(navController, logId, logsViewModel)
            }
        }
    }
}