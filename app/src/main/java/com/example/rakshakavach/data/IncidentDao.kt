package com.example.rakshakavach.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidentDao {
    @Query("SELECT * FROM incident_logs ORDER BY date DESC")
    fun getAllIncidents(): Flow<List<IncidentLog>>

    @Insert
    suspend fun insertIncident(incident: IncidentLog)
}
