package org.ascarafia.modoconciencia.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Upsert
    suspend fun upsert(task: TaskEntity)

    @Query("SELECT * FROM TaskEntity")
    fun getTaskList(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTask(id: String): TaskEntity?

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    suspend fun deleteTask(id: String)
}