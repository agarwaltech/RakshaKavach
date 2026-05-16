package com.example.rakshakavach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rakshakavach.data.local.entity.IncidentEntity
import com.example.rakshakavach.data.local.entity.SeverityLevel
import com.example.rakshakavach.viewmodel.IncidentViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentLogScreen(
    onBack: () -> Unit,
    viewModel: IncidentViewModel = hiltViewModel()
) {
    val incidents by viewModel.incidents.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incident Log") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }, containerColor = MaterialTheme.colorScheme.error) {
                Icon(Icons.Default.Add, contentDescription = "Log Incident", tint = MaterialTheme.colorScheme.onError)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(incidents) { incident ->
                IncidentCard(incident)
            }
        }
    }

    if (showDialog) {
        LogIncidentDialog(
            onDismiss = { showDialog = false },
            onSubmit = { title, desc, loc, type, severity ->
                viewModel.logIncident(title, desc, loc, type, severity)
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentCard(incident: IncidentEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = incident.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Badge(containerColor = if (incident.severity == SeverityLevel.HIGH || incident.severity == SeverityLevel.CRITICAL) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary) {
                    Text(incident.severity.name)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = incident.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Location: ${incident.location} | Date: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(incident.timestamp))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun LogIncidentDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, String, SeverityLevel) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var loc by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf(SeverityLevel.LOW) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Near Miss or Incident") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = loc, onValueChange = { loc = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
                // Simplified severity selection for UI demo
                Text("Severity: ${severity.name}", modifier = Modifier.padding(top = 8.dp))
                Slider(
                    value = severity.ordinal.toFloat(),
                    onValueChange = { severity = SeverityLevel.entries[it.toInt()] },
                    valueRange = 0f..3f,
                    steps = 2
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSubmit(title, desc, loc, type, severity) }) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
