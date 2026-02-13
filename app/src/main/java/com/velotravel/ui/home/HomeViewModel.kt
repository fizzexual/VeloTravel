package com.velotravel.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.velotravel.data.model.Activity
import com.velotravel.data.repository.VeloRepository
import com.velotravel.health.HealthConnectManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
    private val repository: VeloRepository
) : AndroidViewModel(application) {
    
    private val healthConnectManager = HealthConnectManager(application)
    
    val activities: StateFlow<List<Activity>> = repository.getAllActivities()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val totalDistance: StateFlow<Double?> = repository.getTotalDistance()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    
    val totalCalories: StateFlow<Int?> = repository.getTotalCalories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    
    val totalSteps: StateFlow<Int?> = repository.getTotalSteps()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    
    private val _todayStepsFromHealth = MutableStateFlow(0)
    val todayStepsFromHealth: StateFlow<Int> = _todayStepsFromHealth.asStateFlow()
    
    private val _healthConnectAvailable = MutableStateFlow(false)
    val healthConnectAvailable: StateFlow<Boolean> = _healthConnectAvailable.asStateFlow()
    
    init {
        checkHealthConnect()
        loadTodaySteps()
    }
    
    private fun checkHealthConnect() {
        _healthConnectAvailable.value = healthConnectManager.isAvailable()
    }
    
    fun loadTodaySteps() {
        viewModelScope.launch {
            try {
                val steps = healthConnectManager.getTodaySteps()
                _todayStepsFromHealth.value = steps
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun addActivity(activity: Activity) {
        viewModelScope.launch {
            repository.addActivity(activity)
        }
    }
    
    fun deleteActivity(activity: Activity) {
        viewModelScope.launch {
            repository.deleteActivity(activity)
        }
    }
    
    suspend fun hasHealthPermissions(): Boolean {
        return healthConnectManager.hasAllPermissions()
    }
}
