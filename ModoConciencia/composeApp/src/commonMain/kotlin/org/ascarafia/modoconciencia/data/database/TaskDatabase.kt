package org.ascarafia.modoconciencia.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1
)
@ConstructedBy(
    value = TaskDataBaseConstructor::class
)
abstract class TaskDatabase: RoomDatabase() {
    abstract val taskDao: TaskDAO

    companion object {
        const val DB_NAME = "oneTreeTask.db"
    }
}