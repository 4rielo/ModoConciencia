package org.ascarafia.modoconciencia.application.screen_controller

import android.app.Activity

actual class KeepScreenOnProviderFactory(
    private val context: Activity
) {
    actual fun build(): KeepScreenOnController {
        return KeepScreenOnControllerImpl(context)
    }
}