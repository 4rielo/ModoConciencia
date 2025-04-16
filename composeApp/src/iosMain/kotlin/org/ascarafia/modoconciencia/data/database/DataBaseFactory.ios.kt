@file:OptIn(ExperimentalForeignApi::class)

package org.ascarafia.modoconciencia.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DataBaseFactory {
    actual fun create(): RoomDatabase.Builder<LogDatabase> {
        val dbFile = documentDirectory() + "/${LogDatabase.DB_NAME}"
        return Room.databaseBuilder<LogDatabase>(
            name = dbFile
        )
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}