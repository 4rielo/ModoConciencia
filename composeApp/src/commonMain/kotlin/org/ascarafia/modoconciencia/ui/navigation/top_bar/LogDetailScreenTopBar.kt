package org.ascarafia.modoconciencia.ui.navigation.top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.Res
import modoconciencia.composeapp.generated.resources.log_detail_title
import org.ascarafia.modoconciencia.ui.log_list.LogListViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailScreenTopBar(
    navController: NavController,
    logId: String?,
    logsViewModel: LogListViewModel
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Text(
                text = stringResource(Res.string.log_detail_title),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(
                onClick = {
                    logsViewModel.deleteLog(logId = logId.orEmpty())
                    navController.popBackStack()
                }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Log",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}