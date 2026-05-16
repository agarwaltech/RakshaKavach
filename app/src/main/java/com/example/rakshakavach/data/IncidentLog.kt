package com.example.rakshakavach.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incident_logs")
data class IncidentLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val taskName: String,
    val description: String,
    val severity: String // e.g., "Low", "Medium", "High"
)
