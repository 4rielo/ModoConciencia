package org.ascarafia.modoconciencia.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.ascarafia.modoconciencia.domain.model.LogItem

@Entity
data class LogEntity (
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val body: String,
    val date: String,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun toLog(): LogItem {
        return LogItem(
            id = id,
            body = body,
            title = title,
            date = date,
            latitude = latitude,
            longitude = longitude
        )
    }
}