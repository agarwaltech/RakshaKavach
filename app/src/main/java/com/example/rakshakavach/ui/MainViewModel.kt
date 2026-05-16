package com.example.rakshakavach.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rakshakavach.data.AppDatabase
import com.example.rakshakavach.data.IncidentLog
import com.example.rakshakavach.data.SafetyScoreManager
import com.example.rakshakavach.data.SafetyTask
import com.example.rakshakavach.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val incidentDao = db.incidentDao()
    private val safetyScoreManager = SafetyScoreManager(application)

    val tasks = TaskRepository.tasks

    private val _selectedTask = MutableStateFlow<SafetyTask?>(null)
    val selectedTask: StateFlow<SafetyTask?> = _selectedTask

    val safetyScore = safetyScoreManager.safetyScoreFlow
    val consecutiveDays = safetyScoreManager.consecutiveDaysFlow
    
    val recentIncidents = incidentDao.getAllIncidents()

    fun selectTask(task: SafetyTask) {
        _selectedTask.value = task
    }

    fun completeDailyChecklist() {
        viewModelScope.launch {
            safetyScoreManager.recordSafeDay()
        }
    }

    fun logIncident(taskName: String, description: String, severity: String) {
        viewModelScope.launch {
            val incident = IncidentLog(
                date = System.currentTimeMillis(),
                taskName = taskName,
                description = description,
                severity = severity
            )
            incidentDao.insertIncident(incident)
            
            // Penalty based on severity
            val penalty = when (severity) {
                "High" -> 50
                "Medium" -> 20
                else -> 5
            }
            safetyScoreManager.resetConsecutiveDays(penalty)
        }
    }
}
