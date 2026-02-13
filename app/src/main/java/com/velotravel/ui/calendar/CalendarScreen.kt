package com.velotravel.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.velotravel.data.model.Activity
import com.velotravel.data.model.getColor
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    activities: List<Activity>
) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var currentMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    
    // Group activities by date
    val activitiesByDate = activities.groupBy { activity ->
        val cal = Calendar.getInstance()
        cal.timeInMillis = activity.date
        "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}"
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Calendar",
                        fontWeight = FontWeight.SemiBold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1D1D1F)
                )
            )
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Month Navigation
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (currentMonth == 0) {
                            currentMonth = 11
                            currentYear--
                        } else {
                            currentMonth--
                        }
                    }) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Previous month",
                            tint = Color(0xFF007AFF)
                        )
                    }
                    
                    Text(
                        text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(
                            Calendar.getInstance().apply {
                                set(currentYear, currentMonth, 1)
                            }.time
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1D1D1F)
                    )
                    
                    IconButton(onClick = {
                        if (currentMonth == 11) {
                            currentMonth = 0
                            currentYear++
                        } else {
                            currentMonth++
                        }
                    }) {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "Next month",
                            tint = Color(0xFF007AFF)
                        )
                    }
                }
            }
            
            // Calendar Grid
            item {
                CalendarGrid(
                    currentMonth = currentMonth,
                    currentYear = currentYear,
                    activitiesByDate = activitiesByDate,
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )
            }
            
            // Activity Summary
            item {
                Text(
                    text = "Activity Summary",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1D1D1F)
                )
            }
            
            // Stats Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MiniStatCard(
                        title = "Total",
                        value = activities.size.toString(),
                        subtitle = "activities",
                        modifier = Modifier.weight(1f)
                    )
                    MiniStatCard(
                        title = "Distance",
                        value = String.format("%.1f", activities.sumOf { it.distance }),
                        subtitle = "km",
                        modifier = Modifier.weight(1f)
                    )
                    MiniStatCard(
                        title = "Calories",
                        value = activities.sumOf { it.calories }.toString(),
                        subtitle = "kcal",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Activities for selected date or all
            val filteredActivities = if (selectedDate != null) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = selectedDate!!
                val dateKey = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}"
                activitiesByDate[dateKey] ?: emptyList()
            } else {
                activities.take(10)
            }
            
            items(filteredActivities) { activity ->
                CompactActivityCard(activity)
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    currentMonth: Int,
    currentYear: Int,
    activitiesByDate: Map<String, List<Activity>>,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.set(currentYear, currentMonth, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Day headers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF86868B),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Calendar days
            val totalCells = ((daysInMonth + firstDayOfWeek + 6) / 7) * 7
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.height(((totalCells / 7) * 44).dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(totalCells) { index ->
                    val dayNumber = index - firstDayOfWeek + 1
                    if (dayNumber in 1..daysInMonth) {
                        calendar.set(currentYear, currentMonth, dayNumber)
                        val dateKey = "$currentYear-$currentMonth-$dayNumber"
                        val hasActivity = activitiesByDate.containsKey(dateKey)
                        val isSelected = selectedDate != null && isSameDay(selectedDate, calendar.timeInMillis)
                        
                        DayCell(
                            day = dayNumber,
                            hasActivity = hasActivity,
                            isSelected = isSelected,
                            activityCount = activitiesByDate[dateKey]?.size ?: 0,
                            onClick = {
                                onDateSelected(if (isSelected) null else calendar.timeInMillis)
                            }
                        )
                    } else {
                        Box(modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    day: Int,
    hasActivity: Boolean,
    isSelected: Boolean,
    activityCount: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> Color(0xFF007AFF)
                    hasActivity -> Color(0xFFE3F2FD)
                    else -> Color.Transparent
                }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = when {
                    isSelected -> Color.White
                    hasActivity -> Color(0xFF007AFF)
                    else -> Color(0xFF1D1D1F)
                },
                fontWeight = if (hasActivity) FontWeight.Bold else FontWeight.Normal
            )
            if (hasActivity && !isSelected) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF007AFF))
                )
            }
        }
    }
}

@Composable
private fun MiniStatCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F7)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF86868B)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D1D1F)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF86868B)
            )
        }
    }
}

@Composable
private fun CompactActivityCard(activity: Activity) {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(activity.type.getColor())
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.type.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1D1D1F)
                )
                Text(
                    text = "${dateFormat.format(Date(activity.date))} • ${activity.location}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF86868B)
                )
            }
            
            Text(
                text = "${String.format("%.1f", activity.distance)} km",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1D1D1F)
            )
        }
    }
}

private fun isSameDay(date1: Long, date2: Long): Boolean {
    val cal1 = Calendar.getInstance().apply { timeInMillis = date1 }
    val cal2 = Calendar.getInstance().apply { timeInMillis = date2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
           cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}
