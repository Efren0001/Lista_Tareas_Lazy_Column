package com.example.lista_tareas_lazy_column

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lista_tareas_lazy_column.ui.theme.Lista_Tareas_Lazy_ColumnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lista_Tareas_Lazy_ColumnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Task(
    val id: Int,
    val title: String,
    var isCompleted: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskApp() {
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Estudiar", false),
                Task(2, "Estudiar un poco mas", false),
                Task(3, "Salirse del grado", true)
            )
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Lista de Tareas",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(tasks) { task ->
                    TaskItem(task) { updatedTask ->
                        tasks = tasks.map { if (it.id == updatedTask.id) updatedTask else it }
                    }
                }
            }
        }
    )
}

@Composable
fun TaskItem(task: Task, onTaskStatusChanged: (Task) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = if (task.isCompleted) {
                painterResource(id = android.R.drawable.checkbox_on_background)
            } else {
                painterResource(id = android.R.drawable.checkbox_off_background)
            },
            contentDescription = if (task.isCompleted) "Tarea completada" else "Tarea pendiente",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )

        Text(
            text = task.title,
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = { onTaskStatusChanged(task.copy(isCompleted = !task.isCompleted)) },
            colors = ButtonDefaults.buttonColors(containerColor = if (task.isCompleted) Color.Green else Color.Red)
        ) {
            Text(if (task.isCompleted) "Marcar como pendiente" else "Marcar como completada")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskAppPreview() {
    Lista_Tareas_Lazy_ColumnTheme {
        TaskApp()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    TaskApp()
}