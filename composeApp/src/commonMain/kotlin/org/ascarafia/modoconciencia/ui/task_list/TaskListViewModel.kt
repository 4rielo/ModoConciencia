package org.ascarafia.modoconciencia.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ascarafia.modoconciencia.application.location.LocationProvider
import org.ascarafia.modoconciencia.domain.model.Task
import org.ascarafia.modoconciencia.domain.repository.TaskRepository

class TaskListViewModel(
    private val taskRepository: TaskRepository,
    private val locationProvider: LocationProvider
): ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    fun getDatabaseTasks() {
        taskRepository.getTasks()
            .onEach { tasks: List<Task> ->
                _tasks.value = tasks
            }
            .launchIn(viewModelScope)
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                val location = locationProvider.getCurrentLocation()

                val geoTask = task.copy(
                    latitude = location?.coordinates?.latitude,
                    longitude = location?.coordinates?.longitude
                )

                taskRepository.addTask(geoTask)
            } catch (_: Exception) {
                //TODO: handle error/exception. Maybe report to Mixpannel or other service.
            }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.addTask(task)
            } catch (_: Exception) {
                //TODO: handle error/exception. Maybe report to Mixpannel or other service.
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
            } catch (_: Exception) {
                //TODO: handle error/exception. Maybe report to Mixpannel or other service.
            }
        }
    }

    fun toggleTaskCompletion(taskId: String) {
        val index = _tasks.value.indexOfFirst { it.id == taskId }
        if (index != -1) {
            _tasks.value = _tasks.value.toMutableList().apply {
                this[index] = this[index].copy(isCompleted = !this[index].isCompleted)
                updateTask(this[index])
            }
        }
    }

    fun getTaskById(taskId: String): Task? {
        return _tasks.value.find { it.id == taskId }
    }
}