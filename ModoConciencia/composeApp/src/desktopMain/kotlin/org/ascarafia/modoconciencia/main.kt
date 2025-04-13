package org.ascarafia.modoconciencia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ascarafia.modoconciencia.application.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ModoConciencia",
        ) {
            App()
        }
    }
}