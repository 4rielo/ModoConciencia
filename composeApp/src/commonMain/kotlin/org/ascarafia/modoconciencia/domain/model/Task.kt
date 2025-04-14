package org.ascarafia.modoconciencia.domain.model

import org.ascarafia.modoconciencia.data.database.TaskEntity

data class Task(
    val id: String,
    val title: String,
    val body: String,
    val isCompleted: Boolean,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun toTaskEntity(): TaskEntity {
        return TaskEntity(
            id = id,
            body = body,
            title = title,
            isCompleted = isCompleted,
            latitude = latitude,
            longitude = longitude
        )
    }
}