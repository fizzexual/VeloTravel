package com.velotravel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.velotravel.data.model.Achievement
import com.velotravel.data.model.Activity
import com.velotravel.data.model.DailyEntry
import com.velotravel.data.model.UserProgress

@Database(
    entities = [DailyEntry::class, UserProgress::class, Achievement::class, Activity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class VeloDatabase : RoomDatabase() {
    abstract fun dailyEntryDao(): DailyEntryDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun achievementDao(): AchievementDao
    abstract fun activityDao(): ActivityDao
    
    companion object {
        @Volatile
        private var INSTANCE: VeloDatabase? = null
        
        fun getDatabase(context: Context): VeloDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VeloDatabase::class.java,
                    "travel_journeys_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
