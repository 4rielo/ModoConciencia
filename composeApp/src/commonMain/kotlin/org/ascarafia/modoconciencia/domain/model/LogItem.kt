package org.ascarafia.modoconciencia.domain.model

import org.ascarafia.modoconciencia.data.database.LogEntity

data class LogItem(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun toLogEntity(): LogEntity {
        return LogEntity(
            id = id,
            body = body,
            title = title,
            date = date,
            latitude = latitude,
            longitude = longitude
        )
    }
}

fun LogItem?.orEmptyLog(): LogItem {
    return LogItem(id = "", title = "", body = "", date = "")
}