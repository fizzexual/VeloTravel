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
        val allEntriesAllRoutes = dailyEntryDao.getRecentEntries().first()
        
        // Distance milestones (total across all routes)
        val totalKmAllRoutes = allEntriesAllRoutes.sumOf { it.kmRidden }
        when {
            totalKmAllRoutes >= 5000.0 -> unlockAchievement("five_thousand_km")
            totalKmAllRoutes >= 2000.0 -> unlockAchievement("two_thousand_km")
            totalKmAllRoutes >= 1000.0 -> unlockAchievement("thousand_km")
            totalKmAllRoutes >= 500.0 -> unlockAchievement("five_hundred_km")
            totalKmAllRoutes >= 200.0 -> unlockAchievement("two_hundred_km")
            totalKmAllRoutes >= 100.0 -> unlockAchievement("hundred_km")
            totalKmAllRoutes >= 50.0 -> unlockAchievement("first_50km")
            totalKmAllRoutes >= 10.0 -> unlockAchievement("first_10km")
        }
        
        // Streak achievements
        val streakDays = calculateStreak(allEntriesAllRoutes)
        when {
            streakDays >= 100 -> unlockAchievement("hundred_days")
            streakDays >= 30 -> unlockAchievement("month_streak")
            streakDays >= 14 -> unlockAchievement("two_weeks")
            streakDays >= 7 -> unlockAchievement("seven_days")
            streakDays >= 3 -> unlockAchievement("three_days")
        }
        
        // Daily distance achievements
        val todayKm = allEntriesAllRoutes
            .filter { isToday(it.date) }
            .sumOf { it.kmRidden }
        when {
            todayKm >= 100.0 -> unlockAchievement("hundred_km_day")
            todayKm >= 50.0 -> unlockAchievement("fifty_km_day")
            todayKm >= 42.0 -> unlockAchievement("marathon")
            todayKm >= 20.0 -> unlockAchievement("twenty_km_day")
        }
        
        // Route completion achievements
        val progress = userProgressDao.getProgress(routeId).first()
        if (progress?.isCompleted == true) {
            val completedRoutes = getAllRoutes().count { route ->
                val routeProgress = userProgressDao.getProgress(route.id).first()
                routeProgress?.isCompleted == true
            }
            
            when (completedRoutes) {
                1 -> unlockAchievement("first_route")
                3 -> unlockAchievement("three_routes")
                5 -> unlockAchievement("five_routes")
                10 -> unlockAchievement("ten_routes")
            }
            
            if (completedRoutes == getAllRoutes().size) {
                unlockAchievement("all_routes")
            }
            
            // Check if exceeded route distance
            if (totalKm > (getRouteById(routeId)?.totalKm ?: 0.0)) {
                unlockAchievement("overachiever")
            }
        }
        
        // Special achievements
        checkSpecialAchievements(allEntriesAllRoutes)
    }
    
    private suspend fun checkSpecialAchievements(entries: List<DailyEntry>) {
        // Weekend warrior
        if (hasWeekendRides(entries)) {
            unlockAchievement("weekend_warrior")
        }
        
        // Century club (100+ km in a week)
        if (hasWeeklyCentury(entries)) {
            unlockAchievement("century_club")
        }
        
        // Iron legs (7 days, each 20+ km)
        if (hasIronLegsStreak(entries)) {
            unlockAchievement("iron_legs")
        }
        
        // Social rider (10+ entries with notes)
        val entriesWithNotes = entries.count { it.notes.isNotBlank() }
        if (entriesWithNotes >= 10) {
            unlockAchievement("social_rider")
        }
        
        // Explorer (started 5 different routes)
        val uniqueRoutes = entries.map { it.routeId }.distinct().size
        if (uniqueRoutes >= 5) {
            unlockAchievement("explorer")
        }
        
        // Consistent (at least once weekly for a month)
        if (hasConsistentRiding(entries)) {
            unlockAchievement("consistent")
        }
        
        // Check total achievements unlocked
        val unlockedCount = achievementDao.getAllAchievements().first().count { it.isUnlocked }
        if (unlockedCount >= 30) {
            unlockAchievement("legend")
        }
    }
    
    private fun calculateStreak(entries: List<DailyEntry>): Int {
        if (entries.isEmpty()) return 0
        
        val sortedDates = entries.map { it.date }.sorted().reversed()
        var streak = 1
        var maxStreak = 1
        
        for (i in 0 until sortedDates.size - 1) {
            val dayDiff = (sortedDates[i] - sortedDates[i + 1]) / (1000 * 60 * 60 * 24)
            if (dayDiff <= 1) {
                streak++
                maxStreak = maxOf(maxStreak, streak)
            } else {
                streak = 1
            }
        }
        return maxStreak
    }
    
    private fun isToday(timestamp: Long): Boolean {
        val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        val entryDay = timestamp / (1000 * 60 * 60 * 24)
        return today == entryDay
    }
    
    private fun hasWeekendRides(entries: List<DailyEntry>): Boolean {
        val recentEntries = entries.filter { 
            System.currentTimeMillis() - it.date < 7 * 24 * 60 * 60 * 1000 
        }
        val calendar = java.util.Calendar.getInstance()
        var hasSaturday = false
        var hasSunday = false
        
        recentEntries.forEach { entry ->
            calendar.timeInMillis = entry.date
            when (calendar.get(java.util.Calendar.DAY_OF_WEEK)) {
                java.util.Calendar.SATURDAY -> hasSaturday = true
                java.util.Calendar.SUNDAY -> hasSunday = true
            }
        }
        return hasSaturday && hasSunday
    }
    
    private fun hasWeeklyCentury(entries: List<DailyEntry>): Boolean {
        val weekAgo = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        val weeklyKm = entries.filter { it.date >= weekAgo }.sumOf { it.kmRidden }
        return weeklyKm >= 100.0
    }
    
    private fun hasIronLegsStreak(entries: List<DailyEntry>): Boolean {
        val sortedEntries = entries.sortedByDescending { it.date }
        var consecutiveDays = 0
        var lastDate: Long? = null
        
        for (entry in sortedEntries) {
            if (entry.kmRidden < 20.0) continue
            
            if (lastDate == null) {
                consecutiveDays = 1
                lastDate = entry.date
            } else {
                val dayDiff = (lastDate - entry.date) / (1000 * 60 * 60 * 24)
                if (dayDiff <= 1) {
                    consecutiveDays++
                    if (consecutiveDays >= 7) return true
                } else {
                    consecutiveDays = 1
                }
                lastDate = entry.date
            }
        }
        return false
    }
    
    private fun hasConsistentRiding(entries: List<DailyEntry>): Boolean {
        val monthAgo = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000
        val recentEntries = entries.filter { it.date >= monthAgo }
        
        if (recentEntries.isEmpty()) return false
        
        val calendar = java.util.Calendar.getInstance()
        val weeks = mutableSetOf<Int>()
        
        recentEntries.forEach { entry ->
            calendar.timeInMillis = entry.date
            weeks.add(calendar.get(java.util.Calendar.WEEK_OF_YEAR))
        }
        
        return weeks.size >= 4
    }
    
    private fun hasSevenDayStreak(entries: List<DailyEntry>): Boolean {
        return calculateStreak(entries) >= 7
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
