package org.ascarafia.modoconciencia.ui.log_list

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.Res
import modoconciencia.composeapp.generated.resources.create_task_go_back
import modoconciencia.composeapp.generated.resources.log_list_title
import org.ascarafia.modoconciencia.domain.model.LogItem
import org.ascarafia.modoconciencia.ui.log_list.views.LogListItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsListScreenRoot(
    navController: NavController,
    logsViewModel: LogListViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.create_task_go_back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(Res.string.log_list_title),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("createLog/{}")
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
    ) { innerPadding ->
        LaunchedEffect(Unit) {
            logsViewModel.getDatabaseLogs()
        }

        LogListScreen(
            modifier = Modifier
                .padding(innerPadding),
            logsList = logsViewModel.logs.collectAsStateWithLifecycle(),
            openLogDetail = { logId -> navController.navigate("logDetail/$logId") }
        )
    }
}

@Composable
fun LogListScreen(
    modifier: Modifier,
    logsList: State<List<LogItem>>,
    openLogDetail: (logId: String) -> Unit
)  {
    Column(
        modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = logsList.value,
                key = { it.id }
            ) { log ->
                LogListItem(
                    log = log,
                    onClick = {
                        openLogDetail(log.id)
                    }
                )
            }
        }
    }
}
