package org.ascarafia.modoconciencia.application.mp3player

expect class AudioPlayer {
    /** Plays a sound by id (see soundResList) */
    fun playSound(soundFile: String)
    fun release()
}