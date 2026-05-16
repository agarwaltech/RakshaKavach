package com.example.rakshakavach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rakshakavach.data.local.entity.IncidentEntity
import com.example.rakshakavach.data.local.entity.SeverityLevel
import com.example.rakshakavach.domain.repository.SafetyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncidentViewModel @Inject constructor(
    private val repository: SafetyRepository
) : ViewModel() {

    val incidents: StateFlow<List<IncidentEntity>> = repository.getAllIncidents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun logIncident(title: String, desc: String, loc: String, type: String, severity: SeverityLevel) {
        viewModelScope.launch {
            repository.logIncident(
                IncidentEntity(
                    title = title,
                    description = desc,
                    location = loc,
                    taskType = type,
                    severity = severity,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }
}
