package com.velotravel.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val unlockedDate: Long? = null,
    val icon: String = "🏆"
)

// Предефинирани постижения
object AchievementDefinitions {
    val FIRST_10KM = Achievement(
        id = "first_10km",
        title = "Първи 10 км",
        description = "Изминахте първите си 10 километра!",
        icon = "🚴"
    )
    
    val FIRST_50KM = Achievement(
        id = "first_50km",
        title = "Половин стотица",
        description = "Вече сте на 50 км!",
        icon = "💪"
    )
    
    val SEVEN_DAYS_STREAK = Achievement(
        id = "seven_days",
        title = "Седмица подред",
        description = "7 дни последователно каране!",
        icon = "🔥"
    )
    
    val FIRST_ROUTE_COMPLETE = Achievement(
        id = "first_route",
        title = "Първи маршрут",
        description = "Завършихте първия си маршрут!",
        icon = "🎉"
    )
    
    val HUNDRED_KM = Achievement(
        id = "hundred_km",
        title = "Стотица!",
        description = "100 километра изминати!",
        icon = "⭐"
    )
    
    fun getAll() = listOf(
        FIRST_10KM,
        FIRST_50KM,
        SEVEN_DAYS_STREAK,
        FIRST_ROUTE_COMPLETE,
        HUNDRED_KM
    )
}
