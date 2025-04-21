package org.ascarafia.modoconciencia.application.screen_controller

import android.app.Activity
import android.view.WindowManager

actual class KeepScreenOnControllerImpl(private val context: Activity) : KeepScreenOnController {

    actual override fun setKeepScreenOn(enabled: Boolean) {
        val window = context.window
        if (enabled) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}