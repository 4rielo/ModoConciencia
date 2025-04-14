package org.ascarafia.modoconciencia.ui.main_screen

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.*
import org.ascarafia.modoconciencia.ui.main_screen.views.TimerView
import org.ascarafia.modoconciencia.ui.navigation.NavigationDrawer
import org.ascarafia.modoconciencia.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    val mainViewModel: MainViewModel = koinViewModel<MainViewModel>()

    val time by mainViewModel.timeRemaining.collectAsState()
    val isRunning by mainViewModel.isRunning.collectAsState()

    var isDrawerOpen by remember { mutableStateOf(false) }

    AppTheme {
        NavigationDrawer(
            isOpen = isDrawerOpen,
            onClose = { isDrawerOpen = false },
            drawerContent = {
                Text("Opción 1")
                Spacer(Modifier.height(8.dp))
                Text("Opción 2")
                Spacer(Modifier.height(16.dp))
            }
        ) {
            Scaffold(
                topBar = {
                    AnimatedVisibility(
                        !isRunning,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        CenterAlignedTopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            title = {
                                Image(
                                    imageVector = Res.drawable.compose_multiplatform,
                                    contentDescription = null,
                                    modifier = Modifier
                                )
//                                Text(
//                                    stringResource(Res.string.main_screen_title),
//                                    textAlign = TextAlign.Center
//                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = { isDrawerOpen = true }
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
                },

//                floatingActionButton = {
//                    FloatingActionButton(
//                        onClick = {
//                            navController.navigate("createTask")
//                        }
//                    ) {
//                        Icon(Icons.Default.Add, contentDescription = "Agregar Tarea")
//                    }
//                }
            ) { innerPadding ->

                Column (
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimerView(
                        modifier = Modifier
                            .padding(25.dp)
                            .aspectRatio(1F),
                        timeMillis = time,
                        totalTime = mainViewModel.timerValue.value,
                        isRunning = isRunning,
                        onTimeChanged = { mainViewModel.setInitialTime(it) },
                        onPlayPauseClicked = {
                            if (isRunning) mainViewModel.pauseTimer()
                            else mainViewModel.startTimer()
                        },
                    )
                }
            }
        }
    }
}