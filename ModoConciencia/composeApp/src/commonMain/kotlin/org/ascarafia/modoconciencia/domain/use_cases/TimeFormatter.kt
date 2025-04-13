package org.ascarafia.modoconciencia.domain.use_cases

object TimeFormatter {

    private const val SIXTY_SECONDS = 60000

    fun millisToMinuteAndSecs(timeInMillis: Long): String {
        val minutes = timeInMillis / SIXTY_SECONDS

        val seconds = (timeInMillis % SIXTY_SECONDS) / 1000

        return "%02d:%02d".format(minutes, seconds)
    }
}