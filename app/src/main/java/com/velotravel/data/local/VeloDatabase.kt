package com.velotravel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.velotravel.data.model.Achievement
import com.velotravel.data.model.DailyEntry
import com.velotravel.data.model.UserProgress

@Database(
    entities = [DailyEntry::class, UserProgress::class, Achievement::class],
    version = 1,
    exportSchema = false
)
abstract class VeloDatabase : RoomDatabase() {
    abstract fun dailyEntryDao(): DailyEntryDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun achievementDao(): AchievementDao
    
    companion object {
        @Volatile
        private var INSTANCE: VeloDatabase? = null
        
        fun getDatabase(context: Context): VeloDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VeloDatabase::class.java,
                    "velo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
