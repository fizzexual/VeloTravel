package com.velotravel.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_entries")
data class DailyEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val routeId: String,
    val date: Long, // timestamp
    val kmRidden: Double,
    val notes: String = ""
)
