package com.velotravel.data.local

import androidx.room.*
import com.velotravel.data.model.DailyEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyEntryDao {
    @Insert
    suspend fun insert(entry: DailyEntry)
    
    @Query("SELECT * FROM daily_entries WHERE routeId = :routeId ORDER BY date DESC")
    fun getEntriesForRoute(routeId: String): Flow<List<DailyEntry>>
    
    @Query("SELECT * FROM daily_entries ORDER BY date DESC LIMIT 30")
    fun getRecentEntries(): Flow<List<DailyEntry>>
    
    @Query("SELECT SUM(kmRidden) FROM daily_entries WHERE routeId = :routeId")
    suspend fun getTotalKmForRoute(routeId: String): Double?
    
    @Query("SELECT * FROM daily_entries WHERE routeId = :routeId ORDER BY date DESC LIMIT 1")
    suspend fun getLastEntry(routeId: String): DailyEntry?
}
