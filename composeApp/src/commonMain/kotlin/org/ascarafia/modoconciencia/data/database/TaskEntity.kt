package org.ascarafia.modoconciencia.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.ascarafia.modoconciencia.domain.model.Task

@Entity
data class TaskEntity (
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val body: String,
    val isCompleted: Boolean,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun toTask(): Task {
        return Task(
            id = id,
            body = body,
            title = title,
            isCompleted = isCompleted,
            latitude = latitude,
            longitude = longitude
        )
    }
}