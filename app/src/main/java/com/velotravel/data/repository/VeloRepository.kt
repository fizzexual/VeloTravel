package com.velotravel.data.repository

import com.velotravel.data.Routes
import com.velotravel.data.local.AchievementDao
import com.velotravel.data.local.DailyEntryDao
import com.velotravel.data.local.UserProgressDao
import com.velotravel.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class VeloRepository(
    private val dailyEntryDao: DailyEntryDao,
    private val userProgressDao: UserProgressDao,
    private val achievementDao: AchievementDao
) {
    // Routes
    fun getAllRoutes() = Routes.getAll()
    fun getRouteById(id: String) = Routes.getById(id)
    
    // Daily Entries
    suspend fun addDailyEntry(routeId: String, kmRidden: Double, notes: String = "") {
        val entry = DailyEntry(
            routeId = routeId,
            date = System.currentTimeMillis(),
            kmRidden = kmRidden,
            notes = notes
        )
        dailyEntryDao.insert(entry)
        
        // Update progress
        updateProgress(routeId, kmRidden)
        
        // Check achievements
        checkAndUnlockAchievements(routeId)
    }
    
    fun getEntriesForRoute(routeId: String): Flow<List<DailyEntry>> =
        dailyEntryDao.getEntriesForRoute(routeId)
    
    fun getRecentEntries(): Flow<List<DailyEntry>> =
        dailyEntryDao.getRecentEntries()
    
    // User Progress
    private suspend fun updateProgress(routeId: String, additionalKm: Double) {
        val currentProgress = userProgressDao.getProgress(routeId).first()
        val route = getRouteById(routeId) ?: return
        
        if (currentProgress == null) {
            // Create new progress
            userProgressDao.insert(
                UserProgress(
                    routeId = routeId,
                    totalKmCompleted = additionalKm,
                    startDate = System.currentTimeMillis(),
                    lastUpdateDate = System.currentTimeMillis(),
                    isCompleted = additionalKm >= route.totalKm
                )
            )
        } else {
            // Update existing
            val newTotal = currentProgress.totalKmCompleted + additionalKm
            userProgressDao.update(
                currentProgress.copy(
                    totalKmCompleted = newTotal,
                    lastUpdateDate = System.currentTimeMillis(),
                    isCompleted = newTotal >= route.totalKm
                )
            )
        }
    }
    
    fun getProgress(routeId: String): Flow<UserProgress?> =
        userProgressDao.getProgress(routeId)
    
    fun getCurrentActiveRoute(): Flow<UserProgress?> =
        userProgressDao.getCurrentActiveRoute()
    
    suspend fun startNewRoute(routeId: String) {
        userProgressDao.insert(
            UserProgress(
                routeId = routeId,
                totalKmCompleted = 0.0,
                startDate = System.currentTimeMillis(),
                lastUpdateDate = System.currentTimeMillis()
            )
        )
    }
    
    // Achievements
    suspend fun initializeAchievements() {
        AchievementDefinitions.getAll().forEach { achievement ->
            if (achievementDao.getAchievement(achievement.id) == null) {
                achievementDao.insert(achievement)
            }
        }
    }
    
    private suspend fun checkAndUnlockAchievements(routeId: String) {
        val totalKm = dailyEntryDao.getTotalKmForRoute(routeId) ?: 0.0
        val allEntries = dailyEntryDao.getEntriesForRoute(routeId).first()
        
        // Check 10km
        if (totalKm >= 10.0) {
            unlockAchievement("first_10km")
        }
        
        // Check 50km
        if (totalKm >= 50.0) {
            unlockAchievement("first_50km")
        }
        
        // Check 100km
        if (totalKm >= 100.0) {
            unlockAchievement("hundred_km")
        }
        
        // Check 7 days streak
        if (hasSevenDayStreak(allEntries)) {
            unlockAchievement("seven_days")
        }
        
        // Check route completion
        val progress = userProgressDao.getProgress(routeId).first()
        if (progress?.isCompleted == true) {
            unlockAchievement("first_route")
        }
    }
    
    private fun hasSevenDayStreak(entries: List<DailyEntry>): Boolean {
        if (entries.size < 7) return false
        
        val sortedDates = entries.map { it.date }.sorted().reversed()
        var streak = 1
        
        for (i in 0 until sortedDates.size - 1) {
            val dayDiff = (sortedDates[i] - sortedDates[i + 1]) / (1000 * 60 * 60 * 24)
            if (dayDiff <= 1) {
                streak++
                if (streak >= 7) return true
            } else {
                streak = 1
            }
        }
        return false
    }
    
    private suspend fun unlockAchievement(achievementId: String) {
        val achievement = achievementDao.getAchievement(achievementId)
        if (achievement != null && !achievement.isUnlocked) {
            achievementDao.update(
                achievement.copy(
                    isUnlocked = true,
                    unlockedDate = System.currentTimeMillis()
                )
            )
        }
    }
    
    fun getAllAchievements(): Flow<List<Achievement>> =
        achievementDao.getAllAchievements()
}
