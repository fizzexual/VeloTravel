package com.velotravel.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ActivityType(val displayName: String) {
    WALKING("Walking"),
    RUNNING("Running"),
    CYCLING("Cycling"),
    HIKING("Hiking")
}

fun ActivityType.getIcon(): ImageVector {
    return when (this) {
        ActivityType.WALKING -> Icons.Default.Person
        ActivityType.RUNNING -> Icons.Default.Star
        ActivityType.CYCLING -> Icons.Default.Settings
        ActivityType.HIKING -> Icons.Default.Place
    }
}

fun ActivityType.getColor(): Color {
    return when (this) {
        ActivityType.WALKING -> Color(0xFF4CAF50)  // Green
        ActivityType.RUNNING -> Color(0xFFFF5722)  // Deep Orange
        ActivityType.CYCLING -> Color(0xFF2196F3)  // Blue
        ActivityType.HIKING -> Color(0xFF9C27B0)   // Purple
    }
}
