package org.ascarafia.modoconciencia.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDAO {
    @Upsert
    suspend fun upsert(log: LogEntity)

    @Query("SELECT * FROM LogEntity")
    fun getLogList(): Flow<List<LogEntity>>

    @Query("SELECT * FROM LogEntity WHERE id = :id")
    suspend fun getLog(id: String): LogEntity?

    @Query("DELETE FROM LogEntity WHERE id = :id")
    suspend fun deleteLog(id: String)
}