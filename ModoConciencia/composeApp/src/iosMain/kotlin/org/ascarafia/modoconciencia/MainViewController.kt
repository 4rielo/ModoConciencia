package org.ascarafia.modoconciencia

import androidx.compose.ui.window.ComposeUIViewController
import org.ascarafia.modoconciencia.application.di.initKoin

fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
) {
    App()
}