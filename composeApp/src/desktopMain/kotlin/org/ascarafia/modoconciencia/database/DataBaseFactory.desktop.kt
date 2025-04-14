package org.ascarafia.modoconciencia.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DataBaseFactory {
    actual fun create(): RoomDatabase.Builder<TaskDatabase> {
        val os = System.getProperty("os.name").lowercase()

        val userHome = System.getProperty("user.home")

        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "OneTreeTasks")
            os.contains("mac") -> File(userHome, "Library/Application Support/OneTreeTasks")
            else -> File(userHome, ".local/share/OneTreeTasks")
        }

        if(!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, TaskDatabase.DB_NAME)

        return Room.databaseBuilder(dbFile.absolutePath)
    }
}