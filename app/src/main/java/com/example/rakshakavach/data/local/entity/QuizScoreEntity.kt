package com.example.rakshakavach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_score_table")
data class QuizScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateTimestamp: Long,
    val score: Int,
    val totalQuestions: Int,
    val category: String
)
