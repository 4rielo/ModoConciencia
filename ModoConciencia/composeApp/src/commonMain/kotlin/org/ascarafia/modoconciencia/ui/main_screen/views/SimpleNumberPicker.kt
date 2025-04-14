package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SimpleNumberPicker(
    fontSize: TextUnit,
    selected: Int,
    onSelected: (Int) -> Unit,
    maxValue: Int,
    modifier: Modifier = Modifier,
) {
    val items = (0..maxValue).map { it.toString().padStart(2, '0') }
    val state = rememberLazyListState()

    val itemSize = (fontSize.value + 8).dp

    LaunchedEffect(Unit) {
        state.scrollToItem(selected)
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

    LazyColumn(
        state = state,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .height(itemSize)
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
}
