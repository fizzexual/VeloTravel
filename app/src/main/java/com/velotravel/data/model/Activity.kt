package com.velotravel.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: ActivityType,
    val distance: Double, // in km
    val calories: Int,
    val steps: Int,
    val duration: Long, // in minutes
    val date: Long, // timestamp
    val location: String,
    val notes: String = ""
)
