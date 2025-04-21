package org.ascarafia.modoconciencia.application.mp3player

import kotlinx.cinterop.ExperimentalForeignApi
import modoconciencia.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

@OptIn(ExperimentalResourceApi::class)
actual class AudioPlayer {

    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun playSound(soundFile: String) {
        val mediaFile = NSURL.URLWithString(URLString = Res.getUri(soundFile))

        mediaFile?.let {
            audioPlayer = AVAudioPlayer(it, error = null).apply {
                prepareToPlay()
                play()
            }
        }
    }

    actual fun release() {}
}