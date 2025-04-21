package org.ascarafia.modoconciencia.ui.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ascarafia.modoconciencia.application.mp3player.AudioPlayer
import org.ascarafia.modoconciencia.domain.model.TimerGong

class MainViewModel(
    private val audioPlayer: AudioPlayer
): ViewModel() {

    private val _timerValue = MutableStateFlow(600000L)
    val timerValue = _timerValue.asStateFlow()

    private val _timeRemaining = MutableStateFlow(600000L)
    val timeRemaining = _timeRemaining.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private var timerJob: Job? = null

    val gongsOptions = listOf(
        TimerGong(
            gongImage = "Gong 1",
            gongSound = "files/gong_sounds/gong-1.mp3"
        ),
        TimerGong(
            gongImage = "Gong 2",
            gongSound = "files/gong_sounds/gong-2.mp3"
        ),
        TimerGong(
            gongImage = "Gong 3",
            gongSound = "files/gong_sounds/gong-3.mp3"
        ),
        TimerGong(
            gongImage = "Gong 4",
            gongSound = "files/gong_sounds/gong-4.mp3"
        ),
        TimerGong(
            gongImage = "Gong 5",
            gongSound = "files/gong_sounds/gong-5.mp3"
        ),
        TimerGong(
            gongImage = "Gong 6",
            gongSound = "files/gong_sounds/gong-6.mp3"
        ),
        TimerGong(
            gongImage = "Gong 7",
            gongSound = "files/gong_sounds/gong-7.mp3"
        ),
        TimerGong(
            gongImage = "Gong 8",
            gongSound = "files/gong_sounds/gong-8.mp3"
        ),
        TimerGong(
            gongImage = "Gong 9",
            gongSound = "files/gong_sounds/gong-9.mp3"
        ),
        TimerGong(
            gongImage = "Gong 10",
            gongSound = "files/gong_sounds/gong-10.mp3"
        )
    )

    private val _selectedGong = MutableStateFlow(gongsOptions.first())
    val selectedGong = _selectedGong.asStateFlow()

    fun setSelectedGong(newGong: TimerGong) {
        _selectedGong.value = newGong
    }

    fun setInitialTime(millis: Long) {
        _timerValue.value = millis
        resetTimer()
    }

    fun startTimer() {
        if (_isRunning.value || _timeRemaining.value <= 0) return
        _isRunning.value = true

        timerJob = viewModelScope.launch {
            while (_timeRemaining.value > 0 && _isRunning.value) {
                delay(100)
                _timeRemaining.value -= 100
            }
            if (_timeRemaining.value <= 0) {
                //TODO: Finished timer
                timerFinished()
                resetTimer()
            }
            _isRunning.value = false
        }
    }

    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    private fun resetTimer() {
        pauseTimer()
        _timeRemaining.value = _timerValue.value
    }

    private fun timerFinished() {
        audioPlayer.playSound(selectedGong.value.gongSound)
    }
}