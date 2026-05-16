package com.example.rakshakavach.domain.usecase

import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject

class AISafetyService @Inject constructor() {
    
    // In a real app, inject the API key securely or fetch from a backend.
    // For this demonstration, we leave a placeholder.
    private val apiKey = "YOUR_GEMINI_API_KEY_HERE"

    private val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey
        )
    }

    suspend fun getSafetyAdvice(query: String): String {
        if (apiKey == "YOUR_GEMINI_API_KEY_HERE") {
            return "Mock AI Response: Please wear your safety helmet and gloves before starting ${query}. Always inspect your tools. (Replace placeholder API key to enable real GenAI features)"
        }
        return try {
            val prompt = "You are a highly experienced industrial safety expert. Answer this query from a worker: $query"
            val response = generativeModel.generateContent(prompt)
            response.text ?: "I couldn't process that request."
        } catch (e: Exception) {
            "Error connecting to AI Assistant: ${e.localizedMessage}"
        }
    }

    suspend fun getSmartRiskPrediction(taskName: String, missingGears: List<String>): String {
        if (apiKey == "YOUR_GEMINI_API_KEY_HERE") {
            return "Mock Risk Prediction: Without ${missingGears.joinToString(", ")} during $taskName, you are at high risk of severe injury. Please equip them immediately."
        }
        return try {
            val prompt = "I am a worker about to perform '$taskName'. I am NOT wearing the following required safety gear: ${missingGears.joinToString(", ")}. What are the specific smart risk predictions and likely injuries?"
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Risk could not be calculated."
        } catch (e: Exception) {
            "Error calculating risk: ${e.localizedMessage}"
        }
    }
}
