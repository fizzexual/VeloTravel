package com.velotravel.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
        ActivityType.RUNNING -> Icons.Default.Speed
        ActivityType.CYCLING -> Icons.Default.TwoWheeler
        ActivityType.HIKING -> Icons.Default.Landscape
    }
}
