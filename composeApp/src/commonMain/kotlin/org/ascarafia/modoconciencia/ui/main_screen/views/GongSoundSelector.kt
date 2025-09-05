package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ascarafia.modoconciencia.domain.model.TimerGong

@Composable
fun GongSoundSelector(
    modifier: Modifier = Modifier,
    gongsList: List<TimerGong>,
    selectedGong: State<TimerGong>,
    onGongSelected: (TimerGong) -> Unit
) {
    val state = rememberLazyListState()

    LaunchedEffect(Unit) {
        state.scrollToItem(gongsList.indexOf(selectedGong.value))
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress) {
            delay(100)
            val target = state.firstVisibleItemIndex + if (state.firstVisibleItemScrollOffset > 40) 1 else 0
            scope.launch {
                state.animateScrollToItem(target)
                onGongSelected(gongsList[target])
            }
        }
    }
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = {
                val target = state.firstVisibleItemIndex - 1
                if (target >=0 && target < gongsList.count()) {
                    scope.launch {
                        state.animateScrollToItem(target)
                        onGongSelected(gongsList[target])
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .scale(3F)
            )
        }

        LazyRow(
            state = state,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .width(120.dp)
        ) {
            items(gongsList) { item ->

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(120.dp)
                        .padding(20.dp)
                ) {
                    Text(
                        text = item.gongImage,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Button(
            onClick = {
                val target = state.firstVisibleItemIndex + 1
                if (target >=0 && target < gongsList.count()) {
                    scope.launch {
                        state.animateScrollToItem(target)
                        onGongSelected(gongsList[target])
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .scale(3F)
            )
        }
    }
}