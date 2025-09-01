package org.ascarafia.modoconciencia.ui.navigation.top_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import modoconciencia.composeapp.generated.resources.Res
import modoconciencia.composeapp.generated.resources.create_log_go_back
import modoconciencia.composeapp.generated.resources.log_list_title
import modoconciencia.composeapp.generated.resources.topbarlogo2
import org.ascarafia.modoconciencia.ui.log_list.LogListViewModel
import org.ascarafia.modoconciencia.ui.navigation.CreateLogScreenIndex
import org.ascarafia.modoconciencia.ui.navigation.LogDetailScreenIndex
import org.ascarafia.modoconciencia.ui.navigation.LogListScreenIndex
import org.ascarafia.modoconciencia.ui.navigation.MainScreenIndex
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner?,
    hideTopBar: Boolean,
    showDrawer: (Boolean) -> Unit
) {
    AnimatedVisibility(
        !hideTopBar,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

        LaunchedEffect(backStackEntry) {
            showDrawer(false)
        }

        when {
            backStackEntry?.destination?.hasRoute<MainScreenIndex>() == true -> {
                CenterAlignedTopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        Image(
                            painterResource(Res.drawable.topbarlogo2),
                            contentDescription = null
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { showDrawer(true) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "MenuDrawer"
                            )
                        }
                    }
                )
            }

            backStackEntry?.destination?.hasRoute<LogListScreenIndex>() == true -> {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.create_log_go_back),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            stringResource(Res.string.log_list_title),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }

            backStackEntry?.destination?.hasRoute<LogDetailScreenIndex>() == true -> {
                val logDetail: LogDetailScreenIndex? = backStackEntry?.toRoute<LogDetailScreenIndex>()
                val logsViewModel: LogListViewModel = koinViewModel<LogListViewModel>(viewModelStoreOwner = viewModelStoreOwner ?: backStackEntry!!)

                LaunchedEffect(Unit) {
                    print("**** Viewmodel is $logsViewModel")

                    println("***** logsViewModel: ${logsViewModel.logs.value}")
                }
                LogDetailScreenTopBar(
                    navController = navController,
                    logId = logDetail?.logId,
                    logsViewModel = logsViewModel
                )
            }

            backStackEntry?.destination?.hasRoute<CreateLogScreenIndex>() == true -> {
                CreateLogScreenTopBar(
                    navController = navController,
                )
            }

            else -> { }
        }

    }
}