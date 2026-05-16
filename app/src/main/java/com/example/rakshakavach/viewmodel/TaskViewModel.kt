package com.example.rakshakavach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rakshakavach.data.local.entity.RequiredGear
import com.example.rakshakavach.data.local.entity.TaskEntity
import com.example.rakshakavach.domain.repository.SafetyRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.rakshakavach.domain.usecase.AISafetyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: SafetyRepository,
    private val aiSafetyService: AISafetyService
) : ViewModel() {

    val allTasks: StateFlow<List<TaskEntity>> = repository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedTask = MutableStateFlow<TaskEntity?>(null)
    val selectedTask: StateFlow<TaskEntity?> = _selectedTask.asStateFlow()

    private val _requiredGears = MutableStateFlow<List<RequiredGear>>(emptyList())
    val requiredGears: StateFlow<List<RequiredGear>> = _requiredGears.asStateFlow()

    private val _checkedGears = MutableStateFlow<Set<String>>(emptySet())
    val checkedGears: StateFlow<Set<String>> = _checkedGears.asStateFlow()

    fun loadTask(taskId: String) {
        viewModelScope.launch {
            val task = repository.getTaskById(taskId)
            _selectedTask.value = task
            if (task != null) {
                val type = object : TypeToken<List<RequiredGear>>() {}.type
                val gears: List<RequiredGear> = Gson().fromJson(task.requiredGearJson, type)
                _requiredGears.value = gears
                _checkedGears.value = emptySet()
            }
        }
    }

    fun toggleGear(gearId: String) {
        val currentSet = _checkedGears.value.toMutableSet()
        if (currentSet.contains(gearId)) {
            currentSet.remove(gearId)
        } else {
            currentSet.add(gearId)
        }
        _checkedGears.value = currentSet
    }

    fun completeChecklist() {
        viewModelScope.launch {
            // Add safety score for completing checklist correctly
            if (_checkedGears.value.size == _requiredGears.value.size) {
                repository.addSafetyScore(10)
            }
        }
    }

    private val _aiRiskPrediction = MutableStateFlow<String?>(null)
    val aiRiskPrediction: StateFlow<String?> = _aiRiskPrediction.asStateFlow()

    fun getSmartRiskPrediction() {
        val taskName = _selectedTask.value?.name ?: "Unknown Task"
        val missingGearNames = _requiredGears.value.filter { it.id !in _checkedGears.value }.map { it.name }
        
        if (missingGearNames.isEmpty()) {
            _aiRiskPrediction.value = "You are fully equipped. Risk is minimal. Great job!"
            return
        }

        viewModelScope.launch {
            _aiRiskPrediction.value = "Analyzing risk..."
            val prediction = aiSafetyService.getSmartRiskPrediction(taskName, missingGearNames)
            _aiRiskPrediction.value = prediction
        }
    }
}
