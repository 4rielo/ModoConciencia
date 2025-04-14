package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.*
import org.ascarafia.modoconciencia.domain.use_cases.TimeFormatter
import org.ascarafia.modoconciencia.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TimerView(
    modifier: Modifier = Modifier.size(150.dp),
    timeMillis: Long,
    totalTime: Long,
    isRunning: Boolean,
    onTimeChanged: (Long) -> Unit,
    onPlayPauseClicked: () -> Unit
) {
    val strokeWidth = 20.dp
    val timerFontSize = 32.sp

    val totalSeconds = (timeMillis / 1000).coerceAtLeast(0)
    var inputText by remember { mutableStateOf(totalSeconds.toString()) }

    val progressMaxValue = if(totalTime > 0) {
        totalTime.toFloat()
    } else {
        1F
    }
    val progressColor = MaterialTheme.colorScheme.primary

    LaunchedEffect(key1 = isRunning) {
        if (!isRunning) {
            inputText = totalSeconds.toString()
        }
    }

    val progress by animateFloatAsState(
        targetValue = timeMillis / progressMaxValue,
        animationSpec = tween(100),
        label = "progress"
    )

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepAngle = -360 * progress
            val diameterOffset = strokeWidth.toPx() / 2
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(diameterOffset, diameterOffset),
                size = Size(size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx()),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(diameterOffset, diameterOffset),
                size = Size(size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx()),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            if (isRunning) {
                Text(
                    text = TimeFormatter.millisToMinuteAndSecs(timeMillis),
                    fontSize = timerFontSize
                )
            } else {
                CustomTimePicker(
                    modifier = Modifier,
                    currentTime = (timeMillis/1000).toInt(),
                    onTimeChanged = { onTimeChanged(it.toLong() * 1000) },
                    fontSize = timerFontSize
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            IconButton(
                modifier = Modifier
                    .height(100.dp),
                onClick = { onPlayPauseClicked() }
            ) {
                Icon(
                    modifier = Modifier
                        .scale(2F)
                        .fillMaxSize(),
                    imageVector = if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isRunning) "Pausar" else "Iniciar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}