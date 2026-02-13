package com.velotravel.data.local

import androidx.room.TypeConverter
import com.velotravel.data.model.ActivityType

class Converters {
    @TypeConverter
    fun fromActivityType(value: ActivityType): String {
        return value.name
    }
    
    @TypeConverter
    fun toActivityType(value: String): ActivityType {
        return ActivityType.valueOf(value)
    }
}
