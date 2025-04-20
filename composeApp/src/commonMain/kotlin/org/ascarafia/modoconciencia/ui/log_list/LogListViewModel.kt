package org.ascarafia.modoconciencia.ui.log_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ascarafia.modoconciencia.application.location.LocationProvider
import org.ascarafia.modoconciencia.domain.model.LogItem
import org.ascarafia.modoconciencia.domain.repository.LogRepository

class LogListViewModel(
    private val logRepository: LogRepository,
    private val locationProvider: LocationProvider
): ViewModel() {
    private val _logs = MutableStateFlow<List<LogItem>>(emptyList())
    val logs = _logs.asStateFlow()

    fun getDatabaseLogs() {
        logRepository.getLogs()
            .onEach { logs: List<LogItem> ->
                _logs.value = logs
            }
            .launchIn(viewModelScope)
    }

    fun addLog(log: LogItem) {
        viewModelScope.launch {
            try {
                logRepository.addLog(log)
            } catch (_: Exception) {
                //TODO: handle error/exception. Maybe report to Mixpannel or other service.
            }
        }
    }

    private fun updateLog(log: LogItem) {
        viewModelScope.launch {
            try {
                logRepository.addLog(log)
            } catch (_: Exception) {
                //TODO: handle error/exception. Maybe report to Mixpannel or other service.
            }
        }
    }

    fun deleteLog(logId: String) {
        viewModelScope.launch {
            try {
                val log = _logs.value.first { it.id == logId }
                logRepository.deleteLog(log)
            } catch (_: Exception) {
                //TODO: handle error/exception. Maybe report to Mixpannel or other service.
            }
        }
    }

    fun getLogById(logId: String): LogItem? {
        return _logs.value.find { it.id == logId }
    }
}