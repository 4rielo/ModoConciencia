package org.ascarafia.modoconciencia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val LightColorScheme = lightColorScheme(
        primaryContainer = Color(0xFF5CB7BB),
        primary = Color(0xFF5CB7BB),
        secondary = Color(0xFF03DAC5),
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.Black
    )

    val DarkColorScheme = darkColorScheme(
        primaryContainer = Color(0xFF018E96),
        primary = Color(0xFF018E96),
        secondary = Color(0xFF03DAC6),
        background = Color(0xFF121212),
        surface = Color(0xFF121212),
        onPrimary = Color.Black,
        onSecondary = Color.White
    )

    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        content = content
    )
}


private val AppTextInputColors: TextFieldColors
    @Composable
    get() = TextFieldColors(
        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7F),
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        cursorColor = MaterialTheme.colorScheme.onBackground,
        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
        errorTextColor = MaterialTheme.colorScheme.onBackground,
        errorLeadingIconColor = MaterialTheme.colorScheme.onBackground,
        errorTrailingIconColor = MaterialTheme.colorScheme.onBackground,
        errorLabelColor = MaterialTheme.colorScheme.onBackground,
        errorSupportingTextColor = MaterialTheme.colorScheme.error,
        focusedSupportingTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedSupportingTextColor = MaterialTheme.colorScheme.onBackground,
        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7F),
        disabledTextColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7F),
        disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7F),
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        errorCursorColor = MaterialTheme.colorScheme.error,
        textSelectionColors = TODO(),
        focusedIndicatorColor = TODO(),
        unfocusedIndicatorColor = TODO(),
        disabledIndicatorColor = TODO(),
        errorIndicatorColor = TODO(),
        disabledLeadingIconColor = TODO(),
        disabledTrailingIconColor = TODO(),
        disabledLabelColor = TODO(),
        focusedPlaceholderColor = TODO(),
        unfocusedPlaceholderColor = TODO(),
        disabledPlaceholderColor = TODO(),
        errorPlaceholderColor = TODO(),
        disabledSupportingTextColor = TODO(),
        focusedPrefixColor = TODO(),
        unfocusedPrefixColor = TODO(),
        disabledPrefixColor = TODO(),
        errorPrefixColor = TODO(),
        focusedSuffixColor = TODO(),
        unfocusedSuffixColor = TODO(),
        disabledSuffixColor = TODO(),
        errorSuffixColor = TODO()
    )