package org.ascarafia.modoconciencia.ui.task_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.ascarafia.modoconciencia.ui.task_list.TaskListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import modoconciencia.composeapp.generated.resources.Res
import modoconciencia.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TaskDetailScreen(navController: NavController, taskId: String?, viewModel: TaskListViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val task = tasks.find { it.id == taskId } ?: return


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(Res.string.task_detail_title)
                        )
                        IconButton(
                            onClick = {
                                viewModel.deleteTask(task)
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete task"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
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
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Checkbox(
                    checked = task.isCompleted, onCheckedChange = {
                        viewModel.toggleTaskCompletion(task.id)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(8.dp)
            ) {
                Text(text = task.body)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (task.latitude != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(8.dp)
                ) {
                    Text(text = "Dónde fué registrada la tarea: ${task.latitude} ${task.longitude}")
                }
            }
        }
    }
}