package com.example.rakshakavach.di

import android.content.Context
import androidx.room.Room
import com.example.rakshakavach.data.local.AppDatabase
import com.example.rakshakavach.data.local.dao.AppDao
import com.example.rakshakavach.data.repository.SafetyRepositoryImpl
import com.example.rakshakavach.domain.repository.SafetyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rakshakavach_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase): AppDao {
        return database.appDao
    }

    @Provides
    @Singleton
    fun provideSafetyRepository(dao: AppDao): SafetyRepository {
        return SafetyRepositoryImpl(dao)
    }
}
