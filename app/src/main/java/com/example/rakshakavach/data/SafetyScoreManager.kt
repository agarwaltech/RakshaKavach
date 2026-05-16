package com.example.rakshakavach.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "safety_prefs")

class SafetyScoreManager(private val context: Context) {
    private val CONSECUTIVE_DAYS_KEY = intPreferencesKey("consecutive_days")
    private val LAST_LOGGED_DAY_KEY = longPreferencesKey("last_logged_day")
    private val SAFETY_SCORE_KEY = intPreferencesKey("safety_score")

    val consecutiveDaysFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[CONSECUTIVE_DAYS_KEY] ?: 0
        }

    val safetyScoreFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[SAFETY_SCORE_KEY] ?: 100
        }

    suspend fun recordSafeDay() {
        val currentDay = System.currentTimeMillis() / TimeUnit.DAYS.toMillis(1)
        context.dataStore.edit { prefs ->
            val lastDay = prefs[LAST_LOGGED_DAY_KEY] ?: 0L
            if (currentDay > lastDay) {
                val currentConsecutive = prefs[CONSECUTIVE_DAYS_KEY] ?: 0
                prefs[CONSECUTIVE_DAYS_KEY] = currentConsecutive + 1
                prefs[LAST_LOGGED_DAY_KEY] = currentDay
                
                val currentScore = prefs[SAFETY_SCORE_KEY] ?: 100
                prefs[SAFETY_SCORE_KEY] = (currentScore + 5).coerceAtMost(1000)
            }
        }
    }

    suspend fun resetConsecutiveDays(penalty: Int) {
        context.dataStore.edit { prefs ->
            prefs[CONSECUTIVE_DAYS_KEY] = 0
            val currentScore = prefs[SAFETY_SCORE_KEY] ?: 100
            prefs[SAFETY_SCORE_KEY] = (currentScore - penalty).coerceAtLeast(0)
        }
    }
}
