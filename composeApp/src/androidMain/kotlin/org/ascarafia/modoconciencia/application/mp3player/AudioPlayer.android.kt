package org.ascarafia.modoconciencia.application.mp3player

import android.content.Context
import android.media.session.PlaybackState
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import modoconciencia.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
actual class AudioPlayer(private val context: Context) {

    private val mediaPlayer = ExoPlayer.Builder(context).build()

    init {
        mediaPlayer.addListener(
            object: Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    if (playbackState == PlaybackState.STATE_STOPPED) {
                        mediaPlayer.release()
                    }
                }
            }
        )
    }
    actual fun playSound(soundFile: String) {

        val mediaFile = MediaItem.fromUri(Res.getUri(soundFile))
        mediaPlayer.apply {
            setMediaItem(mediaFile)
            playWhenReady = true
            prepare()
            play()
        }
    }

    actual fun release() {
        mediaPlayer.release()
    }
}