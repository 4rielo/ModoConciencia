package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import kotlin.math.atan2
import kotlin.math.*

@Composable
fun RotatableDial(
    progress: Float,
    strokeWidth: Dp = 30.dp,
    timerValue: State<Long>,
    onRotate: (angle: Float) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val progressColor = MaterialTheme.colorScheme.primary
    val lastAngle = remember { mutableStateOf(0F) }
    val currentAngle = remember { mutableStateOf(0F) }

    var center by remember { mutableStateOf(Offset.Zero) }
    val contentBounds = remember { mutableStateOf<Rect?>(null) }


    val showCursor = remember { mutableStateOf(false) }


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    showCursor.value = true
                    val touch = change.position

                    val contentArea = contentBounds.value
                    if (contentArea != null && contentArea.contains(touch)) {
                        showCursor.value = false
                        return@detectDragGestures
                    }

                    val angle2 = calculateAngle(center, dragAmount)
                    currentAngle.value = calculateAngle(center, touch)


                    //val delta = currentAngle.value - lastAngle.value

                    val delta = angle2 - currentAngle.value
//                    val newTimerValue: Long = timerValue.value + (delta * 1000).toLong()
//                    if (newTimerValue > 0) {
//                        onRotate(newTimerValue)
//                    } else {
//                        onRotate(0)
//                    }
                    lastAngle.value = currentAngle.value
                    onRotate(delta)
                    change.consume()
                    //showCursor.value = false
                }
            }
            .onGloballyPositioned {
                center = it.size.center.toOffset()
            }
    ) {
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
            if(showCursor.value) {
                drawArc(
                    color = Color.Red,
                    startAngle = currentAngle.value,
                    sweepAngle = lastAngle.value,
                    useCenter = false,
                    topLeft = Offset(diameterOffset, diameterOffset),
                    size = Size(size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx()),
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .onGloballyPositioned {
                    val position = it.positionInRoot()
                    val size = it.size
                    contentBounds.value = Rect(
                        offset = position,
                        size = Size(size.width.toFloat(), size.height.toFloat())
                    )
                }
        ) {
            content()
        }
    }
}

private fun calculateAngle(center: Offset, point: Offset): Float {
    val dx = point.x - center.x
    val dy = point.y - center.y
    val angleRadians = atan2(dy, dx)
    val angleDegrees = angleRadians * (180f / PI.toFloat())
    return if (angleDegrees < 0) angleDegrees + 360f else angleDegrees
}
