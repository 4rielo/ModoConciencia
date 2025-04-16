package org.ascarafia.modoconciencia.ui.log_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.*
import org.ascarafia.modoconciencia.application.helpers.UUIDGenerator
import org.ascarafia.modoconciencia.domain.model.LogItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLogScreenRoot(
    navController: NavController,
    logId: String?,
    logsViewModel: LogListViewModel = koinViewModel()
) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = stringResource(Res.string.create_task_go_back),
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
                        stringResource(Res.string.create_task_screen_title),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }

    ) { innerPadding ->
        val log = logsViewModel.getLogById(logId.orEmpty())
        CreateLogScreen(
            Modifier
                .padding(innerPadding),
            log = log,
            addNewLog = { newLog ->
                logsViewModel.addLog(newLog)
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun CreateLogScreen(
    modifier: Modifier,
    log: LogItem?,
    addNewLog: (log: LogItem) -> Unit
) {
    var title by remember { mutableStateOf(log?.title.orEmpty()) }
    var body by remember { mutableStateOf(log?.body.orEmpty()) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(Res.string.create_task_add_title)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = body,
            onValueChange = { body = it },
            label = { Text(stringResource(Res.string.create_task_add_description)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (title.isNotBlank() && body.isNotBlank()) {
                val logId = UUIDGenerator().generateUUID()
                addNewLog(
                    LogItem(logId, title, body, "")
                )
            } else {
                //TODO: add toast or message indicating missing fields.
            }
        }) {
            Text(stringResource(Res.string.create_task_save_new_task))
        }
    }
}