package com.example.rakshakavach.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rakshakavach.data.local.dao.AppDao
import com.example.rakshakavach.data.local.entity.IncidentEntity
import com.example.rakshakavach.data.local.entity.QuizScoreEntity
import com.example.rakshakavach.data.local.entity.TaskEntity
import com.example.rakshakavach.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, IncidentEntity::class, TaskEntity::class, QuizScoreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val appDao: AppDao
}
