package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformMainScreen(
    keepScreenOn: Boolean,
    content: @Composable (() -> Unit)
) {
    content()
}