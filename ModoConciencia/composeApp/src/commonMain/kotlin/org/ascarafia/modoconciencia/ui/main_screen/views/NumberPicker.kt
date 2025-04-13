package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SimpleNumberPicker(
    selected: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = (0..99).map { it.toString().padStart(2, '0') }
    val state = rememberLazyListState()

    // Centrar el ítem seleccionado al arrancar
    LaunchedEffect(Unit) {
        state.scrollToItem(selected)
    }

    // Detectar fin del scroll y hacer snap
    val scope = rememberCoroutineScope()
    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress) {
            delay(100) // Dar tiempo a que se estabilice
            val target = state.firstVisibleItemIndex + if (state.firstVisibleItemScrollOffset > 40) 1 else 0
            scope.launch {
                state.animateScrollToItem(target)
                onSelected(target)
            }
        }
    }

    LazyColumn(
        state = state,
        contentPadding = PaddingValues(vertical = 64.dp),
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        itemsIndexed(items) { index, item ->
            val isSelected = index == state.firstVisibleItemIndex + if (state.firstVisibleItemScrollOffset > 40) 1 else 0
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    fontSize = if (isSelected) 32.sp else 20.sp,
                    color = if (isSelected) Color.Black else Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
