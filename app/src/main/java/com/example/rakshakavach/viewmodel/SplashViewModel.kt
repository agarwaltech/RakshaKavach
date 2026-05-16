package com.example.rakshakavach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rakshakavach.data.local.PreferencesManager
import com.example.rakshakavach.data.local.entity.RequiredGear
import com.example.rakshakavach.data.local.entity.RiskLevel
import com.example.rakshakavach.data.local.entity.TaskEntity
import com.example.rakshakavach.data.local.entity.UserEntity
import com.example.rakshakavach.domain.repository.SafetyRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SafetyRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    init {
        initializeApp()
    }

    private fun initializeApp() {
        viewModelScope.launch {
            // Simulate initialization delay for splash animation
            delay(2000)

            // Preload mock data if it's the first time
            val gson = Gson()
            val gearList = listOf(
                RequiredGear("helmet", "Safety Helmet", "ic_helmet", "Protects against falling objects."),
                RequiredGear("gloves", "Safety Gloves", "ic_gloves", "Prevents hand injuries and burns."),
                RequiredGear("boots", "Steel-toe Boots", "ic_boots", "Protects feet from heavy objects.")
            )
            
            val gearJson = gson.toJson(gearList)
            
            repository.preloadTasks(
                listOf(
                    TaskEntity("t1", "Welding", "Factory Work", RiskLevel.HIGH, gearJson, "Ensure no flammable materials are nearby.", "Severe burns, eye damage"),
                    TaskEntity("t2", "Height Work", "Construction", RiskLevel.HIGH, gearJson, "Always secure your harness.", "Fatal falls"),
                    TaskEntity("t3", "Machine Operation", "Factory Work", RiskLevel.MEDIUM, gearJson, "Keep loose clothing away from gears.", "Crush injuries")
                )
            )

            // Ensure a user exists
            repository.saveUser(
                UserEntity(1, "Ravi Kumar", "EMP-409", "Welder", "Maintenance", 150, 12, 5, null)
            )

            _isReady.value = true
        }
    }
}
