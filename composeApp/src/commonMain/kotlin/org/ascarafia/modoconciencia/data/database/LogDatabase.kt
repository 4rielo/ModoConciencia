package org.ascarafia.modoconciencia.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LogEntity::class],
    version = 1
)
@ConstructedBy(
    value = LogDataBaseConstructor::class
)
abstract class LogDatabase: RoomDatabase() {
    abstract val logDao: LogDAO

    companion object {
        const val DB_NAME = "modoConciencia.db"
    }
}