package com.velotravel.data.local

import androidx.room.*
import com.velotravel.data.model.Activity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities ORDER BY date DESC")
    fun getAllActivities(): Flow<List<Activity>>
    
    @Query("SELECT * FROM activities WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getActivitiesByDateRange(startDate: Long, endDate: Long): Flow<List<Activity>>
    
    @Query("SELECT * FROM activities WHERE type = :type ORDER BY date DESC")
    fun getActivitiesByType(type: String): Flow<List<Activity>>
    
    @Query("SELECT SUM(distance) FROM activities")
    fun getTotalDistance(): Flow<Double?>
    
    @Query("SELECT SUM(calories) FROM activities")
    fun getTotalCalories(): Flow<Int?>
    
    @Query("SELECT SUM(steps) FROM activities")
    fun getTotalSteps(): Flow<Int?>
    
    @Insert
    suspend fun insertActivity(activity: Activity): Long
    
    @Update
    suspend fun updateActivity(activity: Activity)
    
    @Delete
    suspend fun deleteActivity(activity: Activity)
    
    @Query("DELETE FROM activities")
    suspend fun deleteAllActivities()
}
