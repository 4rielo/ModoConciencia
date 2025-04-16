package org.ascarafia.modoconciencia.domain.repository

import kotlinx.coroutines.flow.Flow
import org.ascarafia.modoconciencia.domain.model.LogItem

interface LogRepository {
    fun getLogs(): Flow<List<LogItem>>

    suspend fun addLog(newLog: LogItem)

    suspend fun deleteLog(log: LogItem)

    suspend fun getLogById(logId: String): LogItem?
}