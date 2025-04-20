package org.ascarafia.modoconciencia.application.helpers

import kotlinx.datetime.*

fun getCurrentDateTime(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun getFormattedCurrentDate(): String {
    val now = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())

    return "${now.dayOfMonth}/${now.monthNumber}/${now.year}"
}
