package org.ascarafia.modoconciencia.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TaskDataBaseConstructor: RoomDatabaseConstructor<TaskDatabase> {
    override fun initialize(): TaskDatabase
}