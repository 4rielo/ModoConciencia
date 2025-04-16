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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.*
import org.ascarafia.modoconciencia.ui.main_screen.views.TimerView
import org.ascarafia.modoconciencia.ui.navigation.NavigationDrawer
import org.ascarafia.modoconciencia.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MainScreenRoot(
    navController: NavController,
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
) {
    MainScreen(
        timeRemaining = mainViewModel.timeRemaining.collectAsState(),
        isRunning = mainViewModel.isRunning.collectAsState(),
        timerValue = mainViewModel.timerValue.collectAsState(),
        changeTimerValue = { mainViewModel.setInitialTime(it) },
        timerStart = { mainViewModel.startTimer() },
        timerPause = { mainViewModel.pauseTimer() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    timeRemaining: State<Long>,
    isRunning: State<Boolean>,
    timerValue: State<Long>,
    changeTimerValue: (Long) -> Unit,
    timerStart: () -> Unit,
    timerPause: () -> Unit
) {

    var isDrawerOpen by remember { mutableStateOf(false) }

    AppTheme {
        NavigationDrawer(
            isOpen = isDrawerOpen,
            onClose = { isDrawerOpen = false },
            drawerContent = {
                Column(
                    Modifier
                        .padding(top = 50.dp)
                ) {
                    Text("Opción 1")
                    Spacer(Modifier.height(8.dp))
                    Text("Opción 2")
                    Spacer(Modifier.height(16.dp))
                }
            }
        ) {
            Scaffold(
                topBar = {
                    AnimatedVisibility(
                        !isRunning.value,
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
                                    painterResource(Res.drawable.topbarlogo2),
                                    contentDescription = null
                                )
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
                        timeMillis = timeRemaining,
                        totalTime = timerValue,
                        isRunning = isRunning,
                        onTimeChanged = {
                            print("New time is: $it")
                            changeTimerValue(it)
                        },
                        onPlayPauseClicked = {
                            if (isRunning.value) timerPause()
                            else timerStart()
                        },
                    )
                }
            }
        }
    }
}