package org.ascarafia.modoconciencia.ui.main_screen

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavController
import org.ascarafia.modoconciencia.domain.model.TimerGong
import org.ascarafia.modoconciencia.ui.navigation.top_bar.DrawerMenu
import org.ascarafia.modoconciencia.ui.main_screen.views.GongSoundSelector
import org.ascarafia.modoconciencia.ui.main_screen.views.PlatformMainScreen
import org.ascarafia.modoconciencia.ui.main_screen.views.TimerView
import org.ascarafia.modoconciencia.ui.util.DeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MainScreenRoot(
    modifier: Modifier,
    navController: NavController,
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    onTimerRunning: (Boolean) -> Unit
) {
    val isTimerRunning by mainViewModel.isRunning.collectAsState()
    
    LaunchedEffect(isTimerRunning) {
        onTimerRunning(isTimerRunning)
    }

    MainScreen(
        modifier = modifier,
        timeRemaining = mainViewModel.timeRemaining.collectAsState(),
        isRunning = mainViewModel.isRunning.collectAsState(),
        timerValue = mainViewModel.timerValue.collectAsState(),
        changeTimerValue = { mainViewModel.setInitialTime(it) },
        gongsList = mainViewModel.gongsOptions ,
        selectedGong = mainViewModel.selectedGong.collectAsState(),
        onGongSelected = { mainViewModel.setSelectedGong(it) },
        timerStart = { mainViewModel.startTimer() },
        timerPause = { mainViewModel.pauseTimer() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    timeRemaining: State<Long>,
    isRunning: State<Boolean>,
    timerValue: State<Long>,
    changeTimerValue: (Long) -> Unit,
    gongsList: List<TimerGong>,
    selectedGong: State<TimerGong>,
    onGongSelected: (TimerGong) -> Unit,
    timerStart: () -> Unit,
    timerPause: () -> Unit,
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val layout = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    PlatformMainScreen(isRunning.value) {
        when (layout) {
            DeviceConfiguration.MOBILE_PORTRAIT, DeviceConfiguration.TABLET_PORTRAIT -> {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimerView(
                        modifier = Modifier
                            .aspectRatio(1F)
                            .padding(25.dp),
                        timeMillis = timeRemaining,
                        totalTime = timerValue,
                        isRunning = isRunning,
                        onTimeChanged = {
                            changeTimerValue(it)
                        },
                        onPlayPauseClicked = {
                            if (isRunning.value) timerPause()
                            else timerStart()
                        },
                    )

                    if (!isRunning.value) {
                        GongSoundSelector(
                            modifier = Modifier,
                            gongsList = gongsList,
                            selectedGong = selectedGong,
                            onGongSelected = { onGongSelected(it) }
                        )
                    }
                }
            }

            else -> {
                Row(
                    modifier = modifier
                        .windowInsetsPadding(WindowInsets.displayCutout)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TimerView(
                        modifier = Modifier
                            .padding(25.dp)
                            .aspectRatio(1F),
                        timeMillis = timeRemaining,
                        totalTime = timerValue,
                        isRunning = isRunning,
                        onTimeChanged = {
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
        modifier = Modifier,
        timeRemaining= mutableStateOf(1000),
        isRunning= mutableStateOf(true),
        timerValue= mutableStateOf(20000),
        changeTimerValue= {  },
        gongsList= emptyList(),
        selectedGong= mutableStateOf(TimerGong(gongSound = "", gongImage = "")),
        onGongSelected= {},
        timerStart= { },
        timerPause= { }
    )
}