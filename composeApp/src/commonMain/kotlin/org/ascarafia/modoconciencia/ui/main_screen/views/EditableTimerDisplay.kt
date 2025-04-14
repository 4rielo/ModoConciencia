package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ascarafia.modoconciencia.domain.use_cases.TimeFormatter

@Composable
fun EditableTimerDisplay(
    timeMillis: Long,
    totalSeconds: Long,
    isRunning: Boolean,
    onTimeChanged: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    val formattedTime = TimeFormatter.millisToMinuteAndSecs(timeMillis)

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusRequester.requestFocus()
        }
    }

    if (isEditing && !isRunning) {
        TextField(
            value = inputText,
            onValueChange = {
                inputText = it.filter { c -> c.isDigit() || c == ':' }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    parseToMillis(inputText)?.let(onTimeChanged)
                    isEditing = false
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            modifier = modifier
                .width(120.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        isEditing = false
                    }
                }
        )
    } else {
        Text(
            text = formattedTime,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .clickable(enabled = !isRunning) {
                    inputText = formattedTime
                    isEditing = true
                }
        )
    }
}

fun parseToMillis(time: String): Long? {
    val parts = time.split(":")
    return when (parts.size) {
        2 -> {
            val minutes = parts[0].toLongOrNull()
            val seconds = parts[1].toLongOrNull()
            if (minutes != null && seconds != null) {
                (minutes * 60 + seconds) * 1000
            } else null
        }
        1 -> parts[0].toLongOrNull()?.times(1000)
        else -> null
    }
}