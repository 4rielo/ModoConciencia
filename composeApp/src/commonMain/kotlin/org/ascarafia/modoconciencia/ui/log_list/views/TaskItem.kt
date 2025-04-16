package org.ascarafia.modoconciencia.ui.log_list.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.ascarafia.modoconciencia.domain.model.LogItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SwipeToDeleteItem(log: LogItem, onTaskCheckedChange: (Boolean) -> Unit, onDelete: () -> Unit, onClick: () -> Unit) {
    val dismissState = rememberSwipeToDismissBoxState (
        confirmValueChange = {
            false
//            when (it) {
//                SwipeToDismissBoxValue.StartToEnd -> {
//                    onTaskCheckedChange(log.isCompleted)
//                    false
//                }
//                SwipeToDismissBoxValue.EndToStart -> {
//                    onDelete()
//                    true
//                }
//                else -> false
//            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            val color by animateColorAsState(
                when(dismissState.targetValue){
                    SwipeToDismissBoxValue.EndToStart -> androidx.compose.ui.graphics.Color.Red
                    SwipeToDismissBoxValue.StartToEnd -> androidx.compose.ui.graphics.Color.Green
                    else -> androidx.compose.ui.graphics.Color.Transparent
                },
                label = "swipeBackground"
            )
            val icon = when(dismissState.targetValue) {
                SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
                SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Done
                else -> Icons.Default.Done
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 16.dp),
                contentAlignment =
                    if (dismissState.targetValue== SwipeToDismissBoxValue.StartToEnd) {
                        Alignment.CenterStart
                    } else {
                        Alignment.CenterEnd
                    }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Swipe Action",
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }
        },
        content = {
            TaskItem(log = log, onTaskCheckedChange = onTaskCheckedChange, onClick = onClick)
        }
    )
}

@Composable
fun TaskItem(log: LogItem, onTaskCheckedChange: (Boolean) -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
//        Checkbox(
//            checked = log.isCompleted,
//            onCheckedChange = onTaskCheckedChange
//        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = log.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = log.body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}