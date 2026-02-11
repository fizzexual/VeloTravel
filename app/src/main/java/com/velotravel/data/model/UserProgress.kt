package com.velotravel.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey
    val routeId: String,
    val totalKmCompleted: Double = 0.0,
    val startDate: Long,
    val lastUpdateDate: Long,
    val isCompleted: Boolean = false
)
