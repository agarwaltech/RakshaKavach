package com.example.rakshakavach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rakshakavach.data.local.entity.IncidentEntity
import com.example.rakshakavach.data.local.entity.QuizScoreEntity
import com.example.rakshakavach.data.local.entity.TaskEntity
import com.example.rakshakavach.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User
    @Query("SELECT * FROM user_table WHERE id = 1")
    fun getUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE user_table SET safetyScore = safetyScore + :points WHERE id = 1")
    suspend fun addSafetyScore(points: Int)

    // Incidents
    @Query("SELECT * FROM incident_table ORDER BY timestamp DESC")
    fun getAllIncidents(): Flow<List<IncidentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncident(incident: IncidentEntity)

    // Tasks
    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)

    // Quiz
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizScore(score: QuizScoreEntity)

    @Query("SELECT * FROM quiz_score_table ORDER BY dateTimestamp DESC")
    fun getQuizScores(): Flow<List<QuizScoreEntity>>
}
