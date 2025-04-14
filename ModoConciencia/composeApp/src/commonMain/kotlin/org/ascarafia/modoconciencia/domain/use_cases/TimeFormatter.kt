package org.ascarafia.modoconciencia.domain.use_cases

import kotlin.math.roundToInt

object TimeFormatter {

    private const val SIXTY_SECONDS = 60

    fun millisToMinuteAndSecs(timeInMillis: Long): String {
        val time = timeInMillis.div(1000.0).roundToInt()

        val minutes = time / SIXTY_SECONDS

        val seconds = (time % SIXTY_SECONDS)

        return "%02d:%02d".format(minutes, seconds)
    }
}