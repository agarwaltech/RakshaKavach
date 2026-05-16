package com.example.rakshakavach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rakshakavach.data.local.entity.QuizScoreEntity
import com.example.rakshakavach.domain.repository.SafetyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: SafetyRepository
) : ViewModel() {

    private val allQuestions = listOf(
        Question("What is the primary purpose of a safety harness?", listOf("Looking professional", "Preventing fatal falls", "Keeping warm", "Carrying tools"), 1, "Harnesses arrest falls from heights."),
        Question("When should you inspect your PPE?", listOf("Once a month", "Before every use", "Only when it looks broken", "When the supervisor asks"), 1, "Daily pre-use inspection prevents accidents."),
        Question("Which fire extinguisher is used for electrical fires?", listOf("Water", "Foam", "CO2 or Dry Powder", "Wet Chemical"), 2, "Water conducts electricity; CO2 is safe.")
    )

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    val currentQuestion: StateFlow<Question?> = MutableStateFlow(allQuestions.first())

    fun submitAnswer(selectedIndex: Int) {
        val question = allQuestions[_currentQuestionIndex.value]
        if (selectedIndex == question.correctAnswerIndex) {
            _score.value += 1
        }
        
        if (_currentQuestionIndex.value < allQuestions.size - 1) {
            _currentQuestionIndex.value += 1
            (currentQuestion as MutableStateFlow).value = allQuestions[_currentQuestionIndex.value]
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        _isQuizFinished.value = true
        viewModelScope.launch {
            repository.saveQuizScore(
                QuizScoreEntity(
                    dateTimestamp = System.currentTimeMillis(),
                    score = _score.value,
                    totalQuestions = allQuestions.size,
                    category = "General Safety"
                )
            )
            // Add points to global safety score
            repository.addSafetyScore(_score.value * 5)
        }
    }
}
