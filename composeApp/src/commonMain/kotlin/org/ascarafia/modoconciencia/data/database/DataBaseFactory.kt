package org.ascarafia.modoconciencia.data.database

import androidx.room.RoomDatabase

expect class DataBaseFactory {
    fun create(): RoomDatabase.Builder<LogDatabase>
}