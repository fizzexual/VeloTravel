package com.velotravel.health

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZonedDateTime

class HealthConnectManager(private val context: Context) {
    
    private val healthConnectClient by lazy {
        HealthConnectClient.getOrCreate(context)
    }
    
    companion object {
        val PERMISSIONS = setOf(
            HealthPermission.getReadPermission(StepsRecord::class)
        )
    }
    
    suspend fun hasAllPermissions(): Boolean {
        return try {
            val granted = healthConnectClient.permissionController.getGrantedPermissions()
            PERMISSIONS.all { it in granted }
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun getTodaySteps(): Int = withContext(Dispatchers.IO) {
        try {
            if (!hasAllPermissions()) {
                return@withContext 0
            }
            
            val startOfDay = ZonedDateTime.now()
                .toLocalDate()
                .atStartOfDay(ZonedDateTime.now().zone)
                .toInstant()
            
            val endOfDay = Instant.now()
            
            val request = ReadRecordsRequest(
                recordType = StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startOfDay, endOfDay)
            )
            
            val response = healthConnectClient.readRecords(request)
            response.records.sumOf { it.count.toInt() }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
    
    suspend fun getStepsForDateRange(startTime: Instant, endTime: Instant): Int = withContext(Dispatchers.IO) {
        try {
            if (!hasAllPermissions()) {
                return@withContext 0
            }
            
            val request = ReadRecordsRequest(
                recordType = StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
            
            val response = healthConnectClient.readRecords(request)
            response.records.sumOf { it.count.toInt() }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
    
    fun isAvailable(): Boolean {
        return try {
            HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE
        } catch (e: Exception) {
            false
        }
    }
}
