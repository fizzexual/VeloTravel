package com.velotravel.data.repository

import com.velotravel.data.Routes
import com.velotravel.data.local.AchievementDao
import com.velotravel.data.local.ActivityDao
import com.velotravel.data.local.DailyEntryDao
import com.velotravel.data.local.UserProgressDao
import com.velotravel.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class VeloRepository(
    private val dailyEntryDao: DailyEntryDao,
    private val userProgressDao: UserProgressDao,
    private val achievementDao: AchievementDao,
    private val activityDao: ActivityDao
) {
    // Activities
    suspend fun addActivity(activity: Activity): Long {
        val id = activityDao.insertActivity(activity)
        checkAndUnlockAchievements()
        return id
    }
    
    fun getAllActivities(): Flow<List<Activity>> =
        activityDao.getAllActivities()
    
    fun getActivitiesByType(type: ActivityType): Flow<List<Activity>> =
        activityDao.getActivitiesByType(type.name)
    
    fun getTotalDistance(): Flow<Double?> =
        activityDao.getTotalDistance()
    
    fun getTotalCalories(): Flow<Int?> =
        activityDao.getTotalCalories()
    
    fun getTotalSteps(): Flow<Int?> =
        activityDao.getTotalSteps()
    
    suspend fun deleteActivity(activity: Activity) {
        activityDao.deleteActivity(activity)
    }
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
    
    private suspend fun checkAndUnlockAchievements() {
        val allActivities = activityDao.getAllActivities().first()
        
        // Total activities count
        val totalCount = allActivities.size
        when {
            totalCount >= 500 -> unlockAchievement("five_hundred_activities")
            totalCount >= 200 -> unlockAchievement("two_hundred_activities")
            totalCount >= 100 -> unlockAchievement("hundred_activities")
            totalCount >= 50 -> unlockAchievement("fifty_activities")
            totalCount >= 10 -> unlockAchievement("ten_activities")
        }
        
        // Distance milestones
        val totalKm = allActivities.sumOf { it.distance }
        when {
            totalKm >= 10000.0 -> unlockAchievement("distance_master")
            totalKm >= 5000.0 -> unlockAchievement("five_thousand_km")
            totalKm >= 2000.0 -> unlockAchievement("two_thousand_km")
            totalKm >= 1000.0 -> unlockAchievement("thousand_km")
            totalKm >= 500.0 -> unlockAchievement("five_hundred_km")
            totalKm >= 200.0 -> unlockAchievement("two_hundred_km")
            totalKm >= 100.0 -> unlockAchievement("hundred_km")
            totalKm >= 50.0 -> unlockAchievement("first_50km")
            totalKm >= 10.0 -> unlockAchievement("first_10km")
        }
        
        // Steps milestones
        val totalSteps = allActivities.sumOf { it.steps }
        when {
            totalSteps >= 10000000 -> unlockAchievement("step_master")
            totalSteps >= 1000000 -> unlockAchievement("million_steps")
            totalSteps >= 100000 -> unlockAchievement("hundred_k_steps")
            totalSteps >= 50000 -> unlockAchievement("fifty_k_steps")
        }
        
        // Calories milestones
        val totalCals = allActivities.sumOf { it.calories }
        when {
            totalCals >= 500000 -> unlockAchievement("calorie_crusher")
            totalCals >= 100000 -> unlockAchievement("hundred_k_cals")
            totalCals >= 50000 -> unlockAchievement("fifty_k_cals")
            totalCals >= 10000 -> unlockAchievement("ten_k_cals")
        }
        
        // Daily achievements
        val todayActivities = allActivities.filter { isToday(it.timestamp) }
        val todayKm = todayActivities.sumOf { it.distance }
        val todaySteps = todayActivities.sumOf { it.steps }
        val todayCals = todayActivities.sumOf { it.calories }
        
        when {
            todayKm >= 200.0 -> unlockAchievement("ultra_cyclist")
            todayKm >= 100.0 -> unlockAchievement("hundred_km_day")
            todayKm >= 50.0 -> {
                unlockAchievement("fifty_km_day")
                if (todayActivities.any { it.type == ActivityType.Running }) {
                    unlockAchievement("ultra_runner")
                }
            }
            todayKm >= 42.0 -> unlockAchievement("marathon")
            todayKm >= 20.0 -> unlockAchievement("twenty_km_day")
        }
        
        when {
            todaySteps >= 50000 -> unlockAchievement("fifty_k_steps")
            todaySteps >= 20000 -> unlockAchievement("twenty_k_steps")
            todaySteps >= 10000 -> unlockAchievement("ten_k_steps")
            todaySteps >= 5000 -> unlockAchievement("five_k_steps")
            todaySteps >= 1000 -> unlockAchievement("first_1k_steps")
        }
        
        when {
            todayCals >= 2000 -> unlockAchievement("two_thousand_cals")
            todayCals >= 1000 -> unlockAchievement("thousand_cals")
            todayCals >= 500 -> unlockAchievement("five_hundred_cals")
            todayCals >= 100 -> unlockAchievement("hundred_cals")
        }
        
        // Multiple activities in one day
        if (todayActivities.map { it.type }.distinct().size >= 3) {
            unlockAchievement("triple_threat")
        }
        
        // Activity type achievements
        val walkCount = allActivities.count { it.type == ActivityType.Walking }
        val runCount = allActivities.count { it.type == ActivityType.Running }
        val cycleCount = allActivities.count { it.type == ActivityType.Cycling }
        val hikeCount = allActivities.count { it.type == ActivityType.Hiking }
        
        if (walkCount >= 1) unlockAchievement("first_walk")
        if (runCount >= 1) unlockAchievement("first_run")
        if (cycleCount >= 1) unlockAchievement("first_cycle")
        if (hikeCount >= 1) unlockAchievement("first_hike")
        
        if (walkCount >= 100) unlockAchievement("walker_extraordinaire")
        if (walkCount >= 50) unlockAchievement("fifty_walks")
        if (walkCount >= 10) unlockAchievement("ten_walks")
        
        if (runCount >= 50) unlockAchievement("fifty_runs")
        if (runCount >= 10) unlockAchievement("ten_runs")
        
        if (cycleCount >= 50) unlockAchievement("fifty_cycles")
        if (cycleCount >= 10) unlockAchievement("ten_cycles")
        
        if (hikeCount >= 50) {
            unlockAchievement("mountain_goat")
            unlockAchievement("fifty_hikes")
        }
        if (hikeCount >= 10) unlockAchievement("ten_hikes")
        
        if (walkCount >= 1 && runCount >= 1 && cycleCount >= 1 && hikeCount >= 1) {
            unlockAchievement("multi_sport")
        }
        
        // Streak achievements
        val streakDays = calculateActivityStreak(allActivities)
        when {
            streakDays >= 100 -> {
                unlockAchievement("consistency_king")
                unlockAchievement("hundred_days")
            }
            streakDays >= 30 -> unlockAchievement("month_streak")
            streakDays >= 14 -> unlockAchievement("two_weeks")
            streakDays >= 7 -> unlockAchievement("seven_days")
            streakDays >= 3 -> unlockAchievement("three_days")
        }
        
        // Weekly achievements
        checkWeeklyAchievements(allActivities)
        
        // Monthly achievements
        checkMonthlyAchievements(allActivities)
        
        // Special achievements
        checkSpecialAchievements(allActivities)
        
        // Meta achievements
        val unlockedCount = achievementDao.getAllAchievements().first().count { it.isUnlocked }
        when {
            unlockedCount >= 100 -> unlockAchievement("perfectionist")
            unlockedCount >= 90 -> unlockAchievement("master_of_all")
            unlockedCount >= 75 -> unlockAchievement("ultimate_champion")
            unlockedCount >= 50 -> unlockAchievement("super_legend")
            unlockedCount >= 30 -> unlockAchievement("legend")
        }
    }
    
    private fun calculateActivityStreak(activities: List<Activity>): Int {
        if (activities.isEmpty()) return 0
        
        val sortedDates = activities.map { it.timestamp / (1000 * 60 * 60 * 24) }.distinct().sorted().reversed()
        var streak = 1
        var maxStreak = 1
        
        for (i in 0 until sortedDates.size - 1) {
            val dayDiff = sortedDates[i] - sortedDates[i + 1]
            if (dayDiff <= 1) {
                streak++
                maxStreak = maxOf(maxStreak, streak)
            } else {
                streak = 1
            }
        }
        return maxStreak
    }
    
    private suspend fun checkWeeklyAchievements(activities: List<Activity>) {
        val weekAgo = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        val weekActivities = activities.filter { it.timestamp >= weekAgo }
        
        val weekKm = weekActivities.sumOf { it.distance }
        val weekSteps = weekActivities.sumOf { it.steps }
        val weekCals = weekActivities.sumOf { it.calories }
        
        when {
            weekKm >= 100.0 -> {
                unlockAchievement("hundred_km_week")
                unlockAchievement("century_club")
            }
            weekKm >= 50.0 -> unlockAchievement("fifty_km_week")
        }
        
        if (weekSteps >= 50000) unlockAchievement("fifty_k_steps_week")
        if (weekCals >= 5000) unlockAchievement("five_k_cals_week")
        
        // Active every day this week
        val daysWithActivity = weekActivities.map { 
            it.timestamp / (1000 * 60 * 60 * 24) 
        }.distinct().size
        if (daysWithActivity >= 7) unlockAchievement("active_week")
    }
    
    private suspend fun checkMonthlyAchievements(activities: List<Activity>) {
        val monthAgo = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000
        val monthActivities = activities.filter { it.timestamp >= monthAgo }
        
        val monthKm = monthActivities.sumOf { it.distance }
        val monthSteps = monthActivities.sumOf { it.steps }
        val monthCals = monthActivities.sumOf { it.calories }
        
        when {
            monthKm >= 500.0 -> unlockAchievement("five_hundred_km_month")
            monthKm >= 200.0 -> unlockAchievement("two_hundred_km_month")
            monthKm >= 100.0 -> unlockAchievement("hundred_km_month")
        }
        
        if (monthSteps >= 100000) unlockAchievement("hundred_k_steps_month")
        if (monthCals >= 10000) unlockAchievement("ten_k_cals_month")
        
        // Perfect month - activity every day
        val daysWithActivity = monthActivities.map { 
            it.timestamp / (1000 * 60 * 60 * 24) 
        }.distinct().size
        if (daysWithActivity >= 30) unlockAchievement("perfect_month")
        
        // Consistent - at least once weekly for a month
        if (hasConsistentRiding(monthActivities)) {
            unlockAchievement("consistent")
        }
        
        // Weekend champion - activity every weekend for a month
        if (hasWeekendChampion(monthActivities)) {
            unlockAchievement("weekend_champion")
        }
    }
    
    private suspend fun checkSpecialAchievements(activities: List<Activity>) {
        val calendar = java.util.Calendar.getInstance()
        
        // Early bird and night owl
        var earlyCount = 0
        var nightCount = 0
        
        activities.forEach { activity ->
            calendar.timeInMillis = activity.timestamp
            val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
            if (hour < 7) earlyCount++
            if (hour >= 22) nightCount++
        }
        
        if (earlyCount >= 1) unlockAchievement("early_bird")
        if (earlyCount >= 10) unlockAchievement("early_starter")
        if (nightCount >= 1) unlockAchievement("night_rider")
        if (nightCount >= 10) unlockAchievement("night_owl")
        
        // Weekend warrior
        if (hasWeekendRides(activities)) {
            unlockAchievement("weekend_warrior")
        }
        
        // Iron legs (7 days, each 20+ km)
        if (hasIronLegsStreak(activities)) {
            unlockAchievement("iron_legs")
        }
    }
    
    private fun hasConsistentRiding(activities: List<Activity>): Boolean {
        if (activities.isEmpty()) return false
        
        val calendar = java.util.Calendar.getInstance()
        val weeks = mutableSetOf<Int>()
        
        activities.forEach { activity ->
            calendar.timeInMillis = activity.timestamp
            weeks.add(calendar.get(java.util.Calendar.WEEK_OF_YEAR))
        }
        
        return weeks.size >= 4
    }
    
    private fun hasWeekendChampion(activities: List<Activity>): Boolean {
        val calendar = java.util.Calendar.getInstance()
        val weekends = mutableSetOf<Int>()
        
        activities.forEach { activity ->
            calendar.timeInMillis = activity.timestamp
            val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
            if (dayOfWeek == java.util.Calendar.SATURDAY || dayOfWeek == java.util.Calendar.SUNDAY) {
                weekends.add(calendar.get(java.util.Calendar.WEEK_OF_YEAR))
            }
        }
        
        return weekends.size >= 4
    }
    
    private fun hasWeekendRides(activities: List<Activity>): Boolean {
        val recentActivities = activities.filter { 
            System.currentTimeMillis() - it.timestamp < 7 * 24 * 60 * 60 * 1000 
        }
        val calendar = java.util.Calendar.getInstance()
        var hasSaturday = false
        var hasSunday = false
        
        recentActivities.forEach { activity ->
            calendar.timeInMillis = activity.timestamp
            when (calendar.get(java.util.Calendar.DAY_OF_WEEK)) {
                java.util.Calendar.SATURDAY -> hasSaturday = true
                java.util.Calendar.SUNDAY -> hasSunday = true
            }
        }
        return hasSaturday && hasSunday
    }
    
    private fun hasIronLegsStreak(activities: List<Activity>): Boolean {
        val sortedActivities = activities.sortedByDescending { it.timestamp }
        val dailyKm = mutableMapOf<Long, Double>()
        
        sortedActivities.forEach { activity ->
            val day = activity.timestamp / (1000 * 60 * 60 * 24)
            dailyKm[day] = (dailyKm[day] ?: 0.0) + activity.distance
        }
        
        val sortedDays = dailyKm.keys.sorted().reversed()
        var consecutiveDays = 0
        
        for (i in 0 until sortedDays.size - 1) {
            if (dailyKm[sortedDays[i]]!! >= 20.0) {
                val dayDiff = sortedDays[i] - sortedDays[i + 1]
                if (dayDiff <= 1) {
                    consecutiveDays++
                    if (consecutiveDays >= 7) return true
                } else {
                    consecutiveDays = 0
                }
            } else {
                consecutiveDays = 0
            }
        }
        return false
    }
    
    private fun isToday(timestamp: Long): Boolean {
        val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        val activityDay = timestamp / (1000 * 60 * 60 * 24)
        return today == activityDay
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
