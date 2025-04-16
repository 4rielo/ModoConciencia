package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SimpleNumberPicker(
    fontSize: TextUnit,
    selectedValue: State<Int>,
    onSelected: (Int) -> Unit,
    maxValue: Int,
    modifier: Modifier = Modifier,
) {
    val items = (0..maxValue).map { it.toString().padStart(2, '0') }
    val state = rememberLazyListState()

    val itemSize = (fontSize.value + 8).dp

    LaunchedEffect(Unit) {
        state.scrollToItem(selectedValue.value)
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress) {
            delay(100)
            val target = state.firstVisibleItemIndex + if (state.firstVisibleItemScrollOffset > 40) 1 else 0
            scope.launch {
                state.animateScrollToItem(target)
                onSelected(target)
            }
        }
    }

    Column (
        Modifier
            .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val target = state.firstVisibleItemIndex + 1
                if (items.contains(target.toString().padStart(2, '0'))) {
                    scope.launch {
                        state.animateScrollToItem(target)
                        onSelected(target)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .offset(y = 15.dp )
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropUp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .scale(3F)
            )
        }
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .height(itemSize)
                .padding(4.dp)
        ) {
            items(items) { item ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        fontSize = fontSize,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Button(
            onClick = {
                val target = state.firstVisibleItemIndex - 1
                if (items.contains(target.toString().padStart(2, '0'))) {
                    scope.launch {
                        state.animateScrollToItem(target)
                        onSelected(target)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .offset(y = (-15).dp )
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .scale(3F)
            )
        }
    }
}
