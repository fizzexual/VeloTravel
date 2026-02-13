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
        ActivityType.WALKING -> Color(0xFF34C759)  // Apple Green
        ActivityType.RUNNING -> Color(0xFFFF3B30)  // Apple Red
        ActivityType.CYCLING -> Color(0xFF007AFF)  // Apple Blue
        ActivityType.HIKING -> Color(0xFFAF52DE)   // Apple Purple
    }
}
