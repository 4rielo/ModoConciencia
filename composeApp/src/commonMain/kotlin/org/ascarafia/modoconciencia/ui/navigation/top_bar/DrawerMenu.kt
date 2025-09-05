package org.ascarafia.modoconciencia.ui.navigation.top_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ascarafia.modoconciencia.ui.navigation.LogListScreenIndex

@Composable
fun DrawerMenu(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier
    ) {

        Row {
            Icon(
                Icons.Filled.EditNote,
                contentDescription = "Create new Log",
                tint = MaterialTheme.colorScheme.onSecondary,
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = "Bitácora",
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .clickable {
                        navController.navigate(LogListScreenIndex)
                    }
            )
        }

        Spacer(Modifier.height(8.dp))

//        Text(
//            "Opción 2",
//            color = MaterialTheme.colorScheme.onSecondary)
//        Spacer(Modifier.height(16.dp))
    }
}