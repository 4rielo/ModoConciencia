package org.ascarafia.modoconciencia.ui.main_screen.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimePicker(modifier: Modifier, currentTime: Long) {
    var selectedValue by remember { mutableStateOf(9) }

    var selectedValue2 by remember { mutableStateOf(30) }

//    Box(
//        Modifier
//            .fillMaxWidth()
////            .size(300.dp)
//    ) {
        Row(
            modifier
                .height(30.dp)
                .fillMaxWidth()
        ) {
            SimpleNumberPicker(
                modifier = Modifier
                    .height(20.dp),
                selected = selectedValue,
                onSelected = { selectedValue = it }
            )

            Text(":")

            SimpleNumberPicker(
                modifier = Modifier
                    .height(20.dp),
                selected = selectedValue2,
                onSelected = { selectedValue2 = it }
            )
        //}
    }
}