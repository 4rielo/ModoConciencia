package org.ascarafia.modoconciencia.application.mp3player

import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioSystem.getAudioInputStream
import javax.sound.sampled.Line
import javax.sound.sampled.SourceDataLine

actual class AudioPlayer {
    actual fun playSound(soundFile: String) {
        val file = File(soundFile)
        getAudioInputStream(file).use { `in` ->
            val outFormat = getOutFormat(`in`.format)
            val info = Line.Info(SourceDataLine::class.java)
            AudioSystem.getLine(info).use { line ->
                (line as? SourceDataLine)?.let { l ->
                    l.open(outFormat)
                    l.start()
                    stream(getAudioInputStream(outFormat, `in`), l)
                    l.drain()
                    l.stop()
                }
            }
        }
    }

    actual fun release() {
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