package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CustomTimePicker(
    modifier: Modifier,
    currentTime: State<Long>,
    onTimeChanged: (Int) -> Unit,
    fontSize: TextUnit
) {
    val minutes = mutableStateOf(((currentTime.asLongState().value/1000) /60).toInt() )
    val seconds = mutableStateOf(((currentTime.asLongState().value/1000) %60).toInt() )

    Row (
        modifier
            .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleNumberPicker(
            modifier = Modifier,
            fontSize = fontSize,
            selectedValue = minutes,
            maxValue = 99,
            onSelected = {
                if (minutes.value != it) {
                    minutes.value = it
                    val totalTime = minutes.value * 60 + seconds.value
                    onTimeChanged(totalTime)
                }
            }
        )

        Text(
            text = ":",
            fontSize = fontSize
        )

        SimpleNumberPicker(
            modifier = Modifier,
            fontSize = fontSize,
            selectedValue = seconds,
            maxValue = 59,
            onSelected = {
                if (seconds.value != it) {
                    seconds.value = it
                    val totalTime = minutes.value * 60 + seconds.value
                    onTimeChanged(totalTime)
                }
            }
        )
    }
}

