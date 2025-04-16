package org.ascarafia.modoconciencia.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.ascarafia.modoconciencia.data.database.LogDAO
import org.ascarafia.modoconciencia.domain.model.LogItem
import org.ascarafia.modoconciencia.domain.repository.LogRepository

class LogRepositoryImpl(
    private val logDAO: LogDAO
): LogRepository {
    override fun getLogs(): Flow<List<LogItem>> {
        return logDAO.getLogList().map { logEntityList ->
            logEntityList.map { it.toLog() }
        }
    }

    override suspend fun addLog(newLog: LogItem) {
        logDAO.upsert(newLog.toLogEntity())
    }

    override suspend fun deleteLog(log: LogItem) {
        logDAO.deleteLog(log.id)
    }

    override suspend fun getLogById(logId: String): LogItem? {
        return logDAO.getLog(logId)?.toLog()
    }
}