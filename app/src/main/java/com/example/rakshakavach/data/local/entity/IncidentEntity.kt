package com.example.rakshakavach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incident_table")
data class IncidentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val location: String,
    val taskType: String,
    val severity: SeverityLevel,
    val timestamp: Long,
    val photoPath: String? = null
)

enum class SeverityLevel {
    LOW, MEDIUM, HIGH, CRITICAL
}
