package org.ascarafia.modoconciencia.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DataBaseFactory {
    actual fun create(): RoomDatabase.Builder<LogDatabase> {
        val os = System.getProperty("os.name").lowercase()

        val userHome = System.getProperty("user.home")

        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "ModoConciencia")
            os.contains("mac") -> File(userHome, "Library/Application Support/ModoConciencia")
            else -> File(userHome, ".local/share/ModoConciencia")
        }

        if(!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, LogDatabase.DB_NAME)

        return Room.databaseBuilder(dbFile.absolutePath)
    }
}