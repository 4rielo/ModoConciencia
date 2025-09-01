package org.ascarafia.modoconciencia.ui.log_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.*
import org.ascarafia.modoconciencia.application.helpers.UUIDGenerator
import org.ascarafia.modoconciencia.application.helpers.getFormattedCurrentDate
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
    val log = logsViewModel.getLogById(logId.orEmpty())
    CreateLogScreen(
        Modifier,
        log = log,
        addNewLog = { newLog ->
            logsViewModel.addLog(newLog)
            navController.popBackStack()
        }
    )
}


@Composable
fun CreateLogScreen(
    modifier: Modifier,
    log: LogItem?,
    addNewLog: (log: LogItem) -> Unit
) {
    val date = log?.date?:getFormattedCurrentDate()
    var title by remember { mutableStateOf(log?.title.orEmpty()) }
    var body by remember { mutableStateOf(log?.body.orEmpty()) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
                    .weight(1f)
                    .clickable {
                        if (title.isNotBlank() && body.isNotBlank()) {
                            val logId = log?.id?:UUIDGenerator().generateUUID()
                            addNewLog(
                                LogItem(logId, title, body, date)
                            )
                        } else {
                            //TODO: add toast or message indicating missing fields.
                        }
                    }
            ) {
                Text(
                    text = stringResource(Res.string.create_log_save_new_task),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9F),
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7F),
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary
            ),
            onValueChange = { title = it },
            maxLines = 1,
            label = { Text(stringResource(Res.string.create_log_add_title)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = body,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9F),
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7F),
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary
            ),
            onValueChange = { body = it },
            label = { Text(stringResource(Res.string.create_log_add_description)) }
        )
    }
}