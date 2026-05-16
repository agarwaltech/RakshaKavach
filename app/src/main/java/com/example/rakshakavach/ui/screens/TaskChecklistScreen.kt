package com.example.rakshakavach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rakshakavach.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskChecklistScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onChecklistComplete: () -> Unit
) {
    val task by viewModel.selectedTask.collectAsState()
    val checkedItems = remember { mutableStateMapOf<String, Boolean>() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Safety Gear") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        task?.let { currentTask ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    text = currentTask.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Risk Meter
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Risk Level: ${currentTask.riskLevel.name}", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                        Text("Potential Injury without gear: ${currentTask.riskLevel.label}", color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = currentTask.riskLevel.severityPoints / 5f,
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Text("Mandatory Gear Checklist", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
                
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(currentTask.requiredGear) { gear ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Checkbox(
                                checked = checkedItems[gear.name] == true,
                                onCheckedChange = { checkedItems[gear.name] = it },
                                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                            )
                            Text(text = gear.name, color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp)
                        }
                    }
                }
                
                Button(
                    onClick = {
                        viewModel.completeDailyChecklist()
                        onChecklistComplete()
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    enabled = checkedItems.size == currentTask.requiredGear.size && !checkedItems.containsValue(false),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Confirm Gear Checked", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}
