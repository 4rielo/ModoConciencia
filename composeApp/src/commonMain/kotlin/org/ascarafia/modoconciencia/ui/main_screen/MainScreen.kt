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
import org.ascarafia.modoconciencia.application.screen_controller.KeepScreenOnController
import org.ascarafia.modoconciencia.application.screen_controller.KeepScreenOnProviderFactory
import org.ascarafia.modoconciencia.domain.model.TimerGong
import org.ascarafia.modoconciencia.ui.main_screen.views.DrawerMenu
import org.ascarafia.modoconciencia.ui.main_screen.views.GongSoundSelector
import org.ascarafia.modoconciencia.ui.main_screen.views.PlatformMainScreen
import org.ascarafia.modoconciencia.ui.main_screen.views.TimerView
import org.ascarafia.modoconciencia.ui.navigation.NavigationDrawer
import org.ascarafia.modoconciencia.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


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
        gongsList = mainViewModel.gongsOptions ,
        selectedGong = mainViewModel.selectedGong.collectAsState(),
        onGongSelected = { mainViewModel.setSelectedGong(it) },
        timerStart = { mainViewModel.startTimer() },
        timerPause = { mainViewModel.pauseTimer() },
        drawerMenu = { modifier -> DrawerMenu(modifier, navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    timeRemaining: State<Long>,
    isRunning: State<Boolean>,
    timerValue: State<Long>,
    changeTimerValue: (Long) -> Unit,
    gongsList: List<TimerGong>,
    selectedGong: State<TimerGong>,
    onGongSelected: (TimerGong) -> Unit,
    timerStart: () -> Unit,
    timerPause: () -> Unit,
    drawerMenu: @Composable (Modifier) -> Unit
) {

    var isDrawerOpen by remember { mutableStateOf(false) }
    var paddingValues: PaddingValues? = null

    AppTheme {
        NavigationDrawer(
            isOpen = isDrawerOpen,
            onClose = { isDrawerOpen = false },
            drawerContent = {
                drawerMenu(
                    Modifier
                        .padding(top = (paddingValues?.calculateTopPadding()?:50.dp))
                )
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
                }
            ) { innerPadding ->

                paddingValues = innerPadding

                Column (
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    PlatformMainScreen(isRunning.value) {

                    }

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

                    if (!isRunning.value) {
                        GongSoundSelector(
                            gongsList = gongsList,
                            selectedGong = selectedGong,
                            onGongSelected = { onGongSelected(it) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        timeRemaining= mutableStateOf(1000),
        isRunning= mutableStateOf(true),
        timerValue= mutableStateOf(20000),
        changeTimerValue= {  },
        gongsList= emptyList(),
        selectedGong= mutableStateOf(TimerGong(gongSound = "", gongImage = "")),
        onGongSelected= {},
        timerStart= {  },
        timerPause= { },
        drawerMenu= {}
    )
}