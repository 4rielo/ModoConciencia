package org.ascarafia.modoconciencia.application.mp3player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import modoconciencia.composeapp.generated.resources.Res
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Line
import javax.sound.sampled.SourceDataLine

actual class AudioPlayer {
    var job: Job? = null
    actual fun playSound(soundFile: String) {

        job = CoroutineScope(Dispatchers.IO).launch {
            playBiteArrayFrom(soundFile)
        }
    }

    actual fun release() {
    }

    private suspend fun playBiteArrayFrom(soundFile: String) {
        val bytes = Res.readBytes(soundFile)

        // Necesitamos alimentar un AudioInputStream con estos bytes:
        val bais = ByteArrayInputStream(bytes)
        AudioSystem.getAudioInputStream(bais).use { `in` ->
            val outFormat = getOutFormat(`in`.format)
            val info = Line.Info(SourceDataLine::class.java)
            AudioSystem.getLine(info).use { line ->
                (line as? SourceDataLine)?.let { l ->
                    l.open(outFormat)
                    l.start()
                    stream(AudioSystem.getAudioInputStream(outFormat, `in`), l)
                    l.drain()
                    l.stop()
                    job = null
                }
            }
        }
    }

    private fun getOutFormat(inFormat: AudioFormat): AudioFormat {
        val ch = inFormat.channels
        val rate = inFormat.sampleRate
        return AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false)
    }

    private fun stream(`in`: AudioInputStream, line: SourceDataLine) {
        val buffer = ByteArray(65536)
        var n = 0
        while (n != -1) {
            line.write(buffer, 0, n)
            n = `in`.read(buffer, 0, buffer.size)
        }
    }
}