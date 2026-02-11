package com.velotravel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velotravel.data.model.Route
import com.velotravel.data.model.UserProgress
import com.velotravel.data.repository.VeloRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: VeloRepository
) : ViewModel() {
    
    private val _currentRoute = MutableStateFlow<Route?>(null)
    val currentRoute: StateFlow<Route?> = _currentRoute.asStateFlow()
    
    private val _progress = MutableStateFlow<UserProgress?>(null)
    val progress: StateFlow<UserProgress?> = _progress.asStateFlow()
    
    private val _kmInput = MutableStateFlow("")
    val kmInput: StateFlow<String> = _kmInput.asStateFlow()
    
    private val _showSuccess = MutableStateFlow(false)
    val showSuccess: StateFlow<Boolean> = _showSuccess.asStateFlow()
    
    init {
        loadCurrentRoute()
        viewModelScope.launch {
            repository.initializeAchievements()
        }
    }
    
    private fun loadCurrentRoute() {
        viewModelScope.launch {
            repository.getCurrentActiveRoute().collect { userProgress ->
                _progress.value = userProgress
                userProgress?.let {
                    _currentRoute.value = repository.getRouteById(it.routeId)
                }
            }
        }
    }
    
    fun onKmInputChange(value: String) {
        // Позволяваме само числа и точка
        if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*$"))) {
            _kmInput.value = value
        }
    }
    
    fun addKilometers() {
        val km = _kmInput.value.toDoubleOrNull() ?: return
        if (km <= 0) return
        
        val routeId = _progress.value?.routeId ?: return
        
        viewModelScope.launch {
            repository.addDailyEntry(routeId, km)
            _kmInput.value = ""
            _showSuccess.value = true
            
            // Hide success message after 2 seconds
            kotlinx.coroutines.delay(2000)
            _showSuccess.value = false
        }
    }
    
    fun getCurrentMilestone(): String {
        val route = _currentRoute.value ?: return ""
        val currentKm = _progress.value?.totalKmCompleted ?: 0.0
        
        // Намери текущия етап
        val milestone = route.milestones
            .sortedBy { it.kmFromStart }
            .lastOrNull { it.kmFromStart <= currentKm }
        
        return milestone?.cityName ?: route.startCity
    }
    
    fun getNextMilestone(): String? {
        val route = _currentRoute.value ?: return null
        val currentKm = _progress.value?.totalKmCompleted ?: 0.0
        
        return route.milestones
            .sortedBy { it.kmFromStart }
            .firstOrNull { it.kmFromStart > currentKm }
            ?.cityName
    }
    
    fun getProgressPercentage(): Float {
        val route = _currentRoute.value ?: return 0f
        val currentKm = _progress.value?.totalKmCompleted ?: 0.0
        return (currentKm / route.totalKm).toFloat().coerceIn(0f, 1f)
    }
}
