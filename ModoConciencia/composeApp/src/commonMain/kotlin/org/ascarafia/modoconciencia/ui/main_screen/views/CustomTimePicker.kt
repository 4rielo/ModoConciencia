package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CustomTimePicker(modifier: Modifier, currentTime: Int, onTimeChanged: (Int) -> Unit, fontSize: TextUnit) {
    var minutes by remember { mutableStateOf((currentTime/60)) }
    var seconds by remember { mutableStateOf((currentTime%60)) }

    Row {
        SimpleNumberPicker(
            modifier = Modifier,
            fontSize = fontSize,
            selected = minutes,
            maxValue = 99,
            onSelected = {
                minutes = it
                val totalTime = minutes*60 + seconds
                onTimeChanged(totalTime)
            }
        )

        Text(
            text = ":",
            fontSize = fontSize
        )

        SimpleNumberPicker(
            modifier = Modifier,
            fontSize = fontSize,
            selected = seconds,
            maxValue = 59,
            onSelected = {
                seconds = it
                val totalTime = minutes*60 + seconds
                onTimeChanged(totalTime)
            }
        )
    }
}

