package org.ascarafia.modoconciencia.domain.repository

import kotlinx.coroutines.flow.Flow
import org.ascarafia.modoconciencia.domain.model.Task

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun addTask(newTask: Task)

    suspend fun deleteTask(task: Task)

    suspend fun getTaskById(taskId: String): Task?
}