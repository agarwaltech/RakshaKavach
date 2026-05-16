package com.example.rakshakavach.domain.repository

import com.example.rakshakavach.data.local.entity.IncidentEntity
import com.example.rakshakavach.data.local.entity.QuizScoreEntity
import com.example.rakshakavach.data.local.entity.TaskEntity
import com.example.rakshakavach.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface SafetyRepository {
    fun getUser(): Flow<UserEntity?>
    suspend fun saveUser(user: UserEntity)
    suspend fun addSafetyScore(points: Int)

    fun getAllIncidents(): Flow<List<IncidentEntity>>
    suspend fun logIncident(incident: IncidentEntity)

    fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun getTaskById(taskId: String): TaskEntity?
    suspend fun preloadTasks(tasks: List<TaskEntity>)

    fun getQuizScores(): Flow<List<QuizScoreEntity>>
    suspend fun saveQuizScore(score: QuizScoreEntity)
}
