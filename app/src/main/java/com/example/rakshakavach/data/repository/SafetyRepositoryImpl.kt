package com.example.rakshakavach.data.repository

import com.example.rakshakavach.data.local.dao.AppDao
import com.example.rakshakavach.data.local.entity.IncidentEntity
import com.example.rakshakavach.data.local.entity.QuizScoreEntity
import com.example.rakshakavach.data.local.entity.TaskEntity
import com.example.rakshakavach.data.local.entity.UserEntity
import com.example.rakshakavach.domain.repository.SafetyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SafetyRepositoryImpl @Inject constructor(
    private val dao: AppDao
) : SafetyRepository {
    override fun getUser(): Flow<UserEntity?> = dao.getUser()

    override suspend fun saveUser(user: UserEntity) {
        dao.insertUser(user)
    }

    override suspend fun addSafetyScore(points: Int) {
        dao.addSafetyScore(points)
    }

    override fun getAllIncidents(): Flow<List<IncidentEntity>> = dao.getAllIncidents()

    override suspend fun logIncident(incident: IncidentEntity) {
        dao.insertIncident(incident)
    }

    override fun getAllTasks(): Flow<List<TaskEntity>> = dao.getAllTasks()

    override suspend fun getTaskById(taskId: String): TaskEntity? = dao.getTaskById(taskId)

    override suspend fun preloadTasks(tasks: List<TaskEntity>) {
        dao.insertTasks(tasks)
    }

    override fun getQuizScores(): Flow<List<QuizScoreEntity>> = dao.getQuizScores()

    override suspend fun saveQuizScore(score: QuizScoreEntity) {
        dao.insertQuizScore(score)
    }
}
