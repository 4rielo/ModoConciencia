package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.runtime.Composable
import org.ascarafia.modoconciencia.application.screen_controller.KeepScreenOnController
import org.ascarafia.modoconciencia.application.screen_controller.KeepScreenOnProviderFactory
import org.koin.compose.koinInject

@Composable
actual fun PlatformMainScreen(keepScreenOn: Boolean, content: @Composable () -> Unit) {
    val screenOnController: KeepScreenOnController = KeepScreenOnProviderFactory().build()
    screenOnController.setKeepScreenOn(keepScreenOn)
    content()
}