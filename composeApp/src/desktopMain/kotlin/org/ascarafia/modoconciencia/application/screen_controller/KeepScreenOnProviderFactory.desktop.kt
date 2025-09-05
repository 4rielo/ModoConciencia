package org.ascarafia.modoconciencia.application.screen_controller

actual class KeepScreenOnProviderFactory {
    actual fun build(): KeepScreenOnController {
        return KeepScreenOnControllerImpl()
    }
}