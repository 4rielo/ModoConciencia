package org.ascarafia.modoconciencia.application.helpers

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.*

@OptIn(ExperimentalTime::class)
fun getCurrentDateTime(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

@OptIn(ExperimentalTime::class)
fun getFormattedCurrentDate(): String {
    val now = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())

    return "${now.day}/${now.monthNumber}/${now.year}"
}
