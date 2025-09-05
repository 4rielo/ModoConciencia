package org.ascarafia.modoconciencia

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.ascarafia.modoconciencia.ui.navigation.floating_action_button.FloatingAction
import org.ascarafia.modoconciencia.ui.main_screen.MainScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.CreateLogScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.LogsListScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.LogDetailScreenRoot
import org.ascarafia.modoconciencia.ui.log_list.LogListViewModel
import org.ascarafia.modoconciencia.ui.navigation.top_bar.DrawerMenu
import org.ascarafia.modoconciencia.ui.navigation.*
import org.ascarafia.modoconciencia.ui.util.theme.AppTheme
import org.ascarafia.modoconciencia.ui.navigation.top_bar.TopBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()

    val viewModelStoreOwner = LocalViewModelStoreOwner.current

    var openDrawerMenu by remember { mutableStateOf(false) }
    var hideTopBar by remember { mutableStateOf(false) }
    var paddingValues: PaddingValues? = null

    AppTheme {
        NavigationDrawer(
            isOpen = openDrawerMenu,
            onClose = { openDrawerMenu = false },
            drawerContent = {
                DrawerMenu(
                    Modifier
                        .padding(top = (paddingValues?.calculateTopPadding()?:50.dp)),
                    navController
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopBar(
                        navController = navController,
                        viewModelStoreOwner = viewModelStoreOwner,
                        hideTopBar = hideTopBar,
                        showDrawer = { openDrawerMenu = it }
                    )
                },
                floatingActionButton = {
                    FloatingAction(
                        navController = navController
                    )
                }
            ) { innerPadding ->
                paddingValues = innerPadding
                NavHost(
                    modifier = Modifier
                        .padding(innerPadding),
                    navController = navController,
                    startDestination = MainScreenIndex
                ) {
                    composable<MainScreenIndex> {
                        MainScreenRoot(
                            modifier = Modifier,
                            navController = navController,
                            onTimerRunning = {
                                hideTopBar = it
                            }
                        )
                    }

                    composable<LogListScreenIndex> { backStackEntry ->
                        val logsViewModel: LogListViewModel = koinViewModel<LogListViewModel>(viewModelStoreOwner = viewModelStoreOwner ?: backStackEntry)

                        LogsListScreenRoot(
                            modifier = Modifier,
                            navController = navController,
                            logsViewModel = logsViewModel
                        )
                    }

                    composable<LogDetailScreenIndex> { backStackEntry ->
                        val logDetail: LogDetailScreenIndex = backStackEntry.toRoute()
                        val logsViewModel: LogListViewModel = koinViewModel<LogListViewModel>(viewModelStoreOwner = viewModelStoreOwner ?: backStackEntry)

                        LogDetailScreenRoot(navController, logDetail.logId, logsViewModel)
                    }

                    composable<CreateLogScreenIndex> { backStackEntry ->
                        val createNewLog: CreateLogScreenIndex = backStackEntry.toRoute()
                        val logsViewModel: LogListViewModel = koinViewModel<LogListViewModel>(viewModelStoreOwner = viewModelStoreOwner ?: backStackEntry)

                        CreateLogScreenRoot(navController, createNewLog.logId, logsViewModel)
                    }
                }
            }
        }
    }
}