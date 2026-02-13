package com.velotravel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velotravel.data.model.Activity
import com.velotravel.data.repository.VeloRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: VeloRepository
) : ViewModel() {
    
    val activities: StateFlow<List<Activity>> = repository.getAllActivities()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val totalDistance: StateFlow<Double?> = repository.getTotalDistance()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    
    val totalCalories: StateFlow<Int?> = repository.getTotalCalories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    
    val totalSteps: StateFlow<Int?> = repository.getTotalSteps()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    
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
}
