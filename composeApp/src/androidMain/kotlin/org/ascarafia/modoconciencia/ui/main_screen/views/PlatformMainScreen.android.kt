package org.ascarafia.modoconciencia.ui.main_screen.views

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.ascarafia.modoconciencia.application.screen_controller.KeepScreenOnController
import org.ascarafia.modoconciencia.application.screen_controller.KeepScreenOnProviderFactory


@Composable
actual fun PlatformMainScreen(
    keepScreenOn:Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val screenOnController: KeepScreenOnController = KeepScreenOnProviderFactory(activity).build()
    screenOnController.setKeepScreenOn(keepScreenOn)
    content()
}
