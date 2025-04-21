package org.ascarafia.modoconciencia.application.screen_controller

import platform.UIKit.UIApplication

actual class KeepScreenOnControllerImpl : KeepScreenOnController {
    actual override fun setKeepScreenOn(enabled: Boolean) {
        UIApplication.sharedApplication.idleTimerDisabled = enabled
    }
}