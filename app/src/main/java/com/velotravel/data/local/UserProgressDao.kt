package com.velotravel.data.local

import androidx.room.*
import com.velotravel.data.model.UserProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: UserProgress)
    
    @Update
    suspend fun update(progress: UserProgress)
    
    @Query("SELECT * FROM user_progress WHERE routeId = :routeId")
    fun getProgress(routeId: String): Flow<UserProgress?>
    
    @Query("SELECT * FROM user_progress WHERE isCompleted = 0 LIMIT 1")
    fun getCurrentActiveRoute(): Flow<UserProgress?>
    
    @Query("SELECT * FROM user_progress")
    fun getAllProgress(): Flow<List<UserProgress>>
}
