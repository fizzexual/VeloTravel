package com.velotravel

import android.app.Application
import com.velotravel.data.local.VeloDatabase
import com.velotravel.data.repository.VeloRepository

class VeloTravelApp : Application() {
    
    private val database by lazy { VeloDatabase.getDatabase(this) }
    
    val repository by lazy {
        VeloRepository(
            dailyEntryDao = database.dailyEntryDao(),
            userProgressDao = database.userProgressDao(),
            achievementDao = database.achievementDao(),
            activityDao = database.activityDao()
        )
    }
}
