package org.ascarafia.modoconciencia.ui.navigation.floating_action_button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import org.ascarafia.modoconciencia.ui.navigation.CreateLogScreenIndex
import org.ascarafia.modoconciencia.ui.navigation.LogDetailScreenIndex
import org.ascarafia.modoconciencia.ui.navigation.LogListScreenIndex
import org.ascarafia.modoconciencia.ui.navigation.MainScreenIndex

@Composable
fun FloatingAction(navController: NavController) {
    val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

    when {
        backStackEntry?.destination?.hasRoute<MainScreenIndex>() == true -> {}

        backStackEntry?.destination?.hasRoute<LogListScreenIndex>() == true -> {
            FloatingActionButton(
                onClick = {
                    navController.navigate(CreateLogScreenIndex())
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Column (
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.EditNote,
                        contentDescription = "Create new Log",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        "Nuevo registro",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        backStackEntry?.destination?.hasRoute<LogDetailScreenIndex>() == true -> {
            val logDetail: LogDetailScreenIndex? = backStackEntry?.toRoute<LogDetailScreenIndex>()

            IconButton(
                onClick = {
                    navController.navigate(CreateLogScreenIndex("${logDetail?.logId}"))
                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Create new Log",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }

        else -> {}
    }
}