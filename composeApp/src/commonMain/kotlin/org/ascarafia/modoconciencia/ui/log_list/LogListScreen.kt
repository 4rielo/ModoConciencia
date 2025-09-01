package org.ascarafia.modoconciencia.ui.log_list

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import modoconciencia.composeapp.generated.resources.Res
import modoconciencia.composeapp.generated.resources.*
import org.ascarafia.modoconciencia.domain.model.LogItem
import org.ascarafia.modoconciencia.ui.log_list.views.LogListItem
import org.ascarafia.modoconciencia.ui.navigation.CreateLogScreenIndex
import org.ascarafia.modoconciencia.ui.navigation.LogDetailScreenIndex
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsListScreenRoot(
    modifier: Modifier,
    navController: NavController,
    logsViewModel: LogListViewModel
) {
    LaunchedEffect(Unit) {
        logsViewModel.getDatabaseLogs()
    }

    LogListScreen(
        modifier = modifier,
        logsList = logsViewModel.logs.collectAsStateWithLifecycle(),
        openLogDetail = { logId -> navController.navigate(LogDetailScreenIndex(logId) ) }
    )
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

@Preview
@Composable
fun LogListScreenPreview() {
    val logList = MutableStateFlow<List<LogItem>>(emptyList())
    LogListScreen(
        modifier = Modifier,
        logsList = logList.collectAsStateWithLifecycle(),
        openLogDetail = {}
    )
}
