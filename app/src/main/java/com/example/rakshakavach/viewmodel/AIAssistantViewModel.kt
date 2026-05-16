package com.example.rakshakavach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rakshakavach.domain.usecase.AISafetyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(val text: String, val isUser: Boolean)

@HiltViewModel
class AIAssistantViewModel @Inject constructor(
    private val aiSafetyService: AISafetyService
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("Hello! I am your AI Safety Assistant. How can I help you stay safe today?", false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(query: String) {
        if (query.isBlank()) return

        val userMessage = ChatMessage(query, true)
        _messages.value = _messages.value + userMessage
        _isLoading.value = true

        viewModelScope.launch {
            val aiResponseText = aiSafetyService.getSafetyAdvice(query)
            val aiMessage = ChatMessage(aiResponseText, false)
            _messages.value = _messages.value + aiMessage
            _isLoading.value = false
        }
    }
}
