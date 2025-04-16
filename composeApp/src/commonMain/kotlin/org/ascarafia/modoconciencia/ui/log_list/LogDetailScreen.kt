package org.ascarafia.modoconciencia.ui.log_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.Res
import modoconciencia.composeapp.generated.resources.*
import org.ascarafia.modoconciencia.domain.model.LogItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailScreenRoot(
    navController: NavController,
    logId: String?,
    logsViewModel: LogListViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
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
                        text = stringResource(Res.string.task_detail_title),
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
                            contentDescription = "Delete task",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    navController.navigate("createLog/$logId")
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
    ) { innerPadding ->
        logsViewModel.getLogById(logId.orEmpty())?.let {
            LogDetailScreen(
                log = it,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }?: navController.popBackStack()
    }
}

@Composable
fun LogDetailScreen(
    log: LogItem,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = log.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        ) {
            Text(
                text = log.body,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

//        if (log.latitude != null) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(MaterialTheme.colorScheme.tertiaryContainer)
//                    .padding(8.dp)
//            ) {
//                Text(text = "Dónde fué registrada la tarea: ${log.latitude} ${log.longitude}")
//            }
//        }
    }
}