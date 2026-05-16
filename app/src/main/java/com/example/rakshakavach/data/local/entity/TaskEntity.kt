package com.example.rakshakavach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val riskLevel: RiskLevel,
    val requiredGearJson: String, // JSON list of RequiredGear objects
    val safetyInstructions: String,
    val possibleInjuries: String
)

enum class RiskLevel {
    LOW, MEDIUM, HIGH
}

data class RequiredGear(
    val id: String,
    val name: String,
    val iconResName: String,
    val safetyTip: String
)
