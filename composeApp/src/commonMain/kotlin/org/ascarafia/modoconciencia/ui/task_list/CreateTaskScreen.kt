package org.ascarafia.modoconciencia.ui.task_list

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
import org.ascarafia.modoconciencia.domain.model.Task
import org.ascarafia.modoconciencia.ui.task_list.TaskListViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(navController: NavController, viewModel: TaskListViewModel) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(Res.string.create_task_go_back))
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(Res.string.create_task_screen_title))
                }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
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
                    val taskId = UUIDGenerator().generateUUID()
                    viewModel.addTask(Task(taskId, title, body, false))
                    navController.popBackStack()
                } else {
                    //TODO: add toast or message indicating missing fields.
                }
            }) {
                Text(stringResource(Res.string.create_task_save_new_task))
            }
        }
    }
}