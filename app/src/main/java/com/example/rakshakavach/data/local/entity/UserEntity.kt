package com.example.rakshakavach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: Int = 1, // Single user app
    val name: String,
    val workerId: String,
    val role: String,
    val department: String,
    val safetyScore: Int = 0,
    val streakDays: Int = 0,
    val quizzesCompleted: Int = 0,
    val profileImagePath: String? = null
)
