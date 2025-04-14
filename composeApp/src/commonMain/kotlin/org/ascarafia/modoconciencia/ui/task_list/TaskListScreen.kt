package org.ascarafia.modoconciencia.ui.task_list

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ascarafia.modoconciencia.domain.model.Task
import org.ascarafia.modoconciencia.ui.task_list.views.SwipeToDeleteItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskListViewModel = viewModel())  {
    val tasks: List<Task> by viewModel.tasks.collectAsStateWithLifecycle()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        //Text(stringResource(Res.string.task_list_title))
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("createTask")
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Tarea")
                }
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = tasks,
                        key = { it.id }
                    ) { task ->
                        SwipeToDeleteItem(
                            task = task,
                            onTaskCheckedChange = { isChecked ->
                                viewModel.toggleTaskCompletion(task.id)
                            },
                            onClick = {
                                navController.navigate("taskDetail/${task.id}")
                            },
                            onDelete = {
                                viewModel.deleteTask(task)
                            }
                        )
                    }
                }
            }
        }
    }
}