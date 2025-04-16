package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.*
import org.ascarafia.modoconciencia.domain.use_cases.TimeFormatter

@Composable
fun TimerView(
    modifier: Modifier = Modifier.size(150.dp),
    timeMillis: State<Long>,
    totalTime: State<Long>,
    isRunning: State<Boolean>,
    onTimeChanged: (Long) -> Unit,
    onPlayPauseClicked: () -> Unit
) {
    val timerFontSize = 48.sp

    val progressMaxValue = if(totalTime.value > 0) {
        totalTime.value.toFloat()
    } else {
        1F
    }

    val progress = animateFloatAsState(
        targetValue = timeMillis.value / progressMaxValue,
        animationSpec = tween(100),
        label = "progress"
    )

    ProgressDial(
        modifier = modifier,
        progress = progress,

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            if (isRunning.value) {
                Text(
                    text = TimeFormatter.millisToMinuteAndSecs(timeMillis.value),
                    fontSize = timerFontSize
                )
            } else {
                CustomTimePicker(
                    modifier = Modifier,
                    currentTime = timeMillis,
                    onTimeChanged = { onTimeChanged(it.toLong() * 1000) },
                    fontSize = timerFontSize
                )
            }

            if (isRunning.value) {
                Spacer(modifier = Modifier.height(24.dp))
            }

            IconButton(
                modifier = Modifier
                    .height(100.dp),
                onClick = { onPlayPauseClicked() }
            ) {
                Icon(
                    modifier = Modifier
                        .scale(2F)
                        .fillMaxSize(),
                    imageVector = if (isRunning.value) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isRunning.value) "Pausar" else "Iniciar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}