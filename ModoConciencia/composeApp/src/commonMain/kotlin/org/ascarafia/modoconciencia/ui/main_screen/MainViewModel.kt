package org.ascarafia.modoconciencia.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _timerValue = MutableStateFlow(600000L)
    val timerValue = _timerValue.asStateFlow()

    private val _timeRemaining = MutableStateFlow(600000L)
    val timeRemaining = _timeRemaining.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private var timerJob: Job? = null

    fun setInitialTime(seconds: Long) {
        _timerValue.value = seconds
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
            _isRunning.value = false
        }
    }

    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        pauseTimer()
        _timeRemaining.value = _timerValue.value
    }
}