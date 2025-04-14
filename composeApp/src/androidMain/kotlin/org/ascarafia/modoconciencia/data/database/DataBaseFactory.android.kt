package org.ascarafia.modoconciencia.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DataBaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<TaskDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(TaskDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}