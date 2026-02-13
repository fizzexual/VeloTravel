package com.velotravel.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.velotravel.data.model.Activity
import com.velotravel.data.model.ActivityType
import com.velotravel.data.model.getIcon
import com.velotravel.data.model.getColor
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSelectRoute: () -> Unit,
    onViewHistory: () -> Unit,
    onViewAchievements: () -> Unit
) {
    val activities by viewModel.activities.collectAsState()
    val totalDistance by viewModel.totalDistance.collectAsState()
    val totalCalories by viewModel.totalCalories.collectAsState()
    val totalSteps by viewModel.totalSteps.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Travel Journeys", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1D1D1F)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF007AFF)
            ) {
                Icon(Icons.Default.Add, "Add Activity", tint = Color.White)
            }
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Stats Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Distance",
                        value = String.format("%.1f", totalDistance ?: 0.0),
                        unit = "km",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Calories",
                        value = (totalCalories ?: 0).toString(),
                        unit = "kcal",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            item {
                StatCard(
                    title = "Steps",
                    value = (totalSteps ?: 0).toString(),
                    unit = "steps",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Recent Activities
            item {
                Text(
                    text = "Recent Activities",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            if (activities.isEmpty()) {
                item {
                    EmptyState()
                }
            } else {
                items(activities) { activity ->
                    ActivityCard(activity = activity)
                }
            }
        }
    }
    
    if (showAddDialog) {
        AddActivityDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { activity ->
                viewModel.addActivity(activity)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    // Animated scale effect
    var isVisible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    LaunchedEffect(Unit) {
        isVisible = true
    }
    
    Card(
        modifier = modifier.scale(scale),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityCard(activity: Activity) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
    
    // Animated appearance
    var isVisible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.9f,
        animationSpec = tween(durationMillis = 300)
    )
    
    LaunchedEffect(Unit) {
        isVisible = true
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = activity.type.getColor().copy(alpha = 0.2f)
            ) {
                Icon(
                    imageVector = activity.type.getIcon(),
                    contentDescription = activity.type.displayName,
                    modifier = Modifier.padding(12.dp),
                    tint = activity.type.getColor()
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.type.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = activity.type.getColor()
                )
                Text(
                    text = activity.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = dateFormat.format(Date(activity.date)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${String.format("%.1f", activity.distance)} km",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${activity.calories} kcal",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                if (activity.steps > 0) {
                    Text(
                        text = "${activity.steps} steps",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No activities yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap the + button to log your first journey",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddActivityDialog(
    onDismiss: () -> Unit,
    onAdd: (Activity) -> Unit
) {
    var selectedType by remember { mutableStateOf(ActivityType.WALKING) }
    var distance by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Activity",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1D1D1F)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, "Close", tint = Color(0xFF86868B))
                    }
                }
                
                // Activity Type Selector
                Text(
                    text = "Activity Type",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1D1D1F)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActivityType.values().forEach { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { selectedType = type },
                            label = { Text(type.displayName, fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = type.getIcon(),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF007AFF),
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFFF5F5F7)
                            )
                        )
                    }
                }
                
                // Required Fields
                OutlinedTextField(
                    value = distance,
                    onValueChange = { if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) distance = it },
                    label = { Text("Distance (km) *", color = Color(0xFF86868B)) },
                    placeholder = { Text("0.0") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF007AFF),
                        unfocusedBorderColor = Color(0xFFE5E5EA),
                        focusedLabelColor = Color(0xFF007AFF),
                        cursorColor = Color(0xFF007AFF)
                    )
                )
                
                OutlinedTextField(
                    value = calories,
                    onValueChange = { if (it.isEmpty() || it.matches(Regex("^\\d*$"))) calories = it },
                    label = { Text("Calories (kcal) *", color = Color(0xFF86868B)) },
                    placeholder = { Text("0") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF007AFF),
                        unfocusedBorderColor = Color(0xFFE5E5EA),
                        focusedLabelColor = Color(0xFF007AFF),
                        cursorColor = Color(0xFF007AFF)
                    )
                )
                
                OutlinedTextField(
                    value = steps,
                    onValueChange = { if (it.isEmpty() || it.matches(Regex("^\\d*$"))) steps = it },
                    label = { Text("Steps *", color = Color(0xFF86868B)) },
                    placeholder = { Text("0") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF007AFF),
                        unfocusedBorderColor = Color(0xFFE5E5EA),
                        focusedLabelColor = Color(0xFF007AFF),
                        cursorColor = Color(0xFF007AFF)
                    )
                )
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF007AFF)
                        )
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            val activity = Activity(
                                type = selectedType,
                                distance = distance.toDoubleOrNull() ?: 0.0,
                                calories = calories.toIntOrNull() ?: 0,
                                steps = steps.toIntOrNull() ?: 0,
                                duration = 0,
                                date = System.currentTimeMillis(),
                                location = "Activity",
                                notes = ""
                            )
                            onAdd(activity)
                        },
                        enabled = distance.toDoubleOrNull() != null && 
                                 distance.toDoubleOrNull()!! > 0 &&
                                 calories.toIntOrNull() != null &&
                                 steps.toIntOrNull() != null,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF007AFF),
                            disabledContainerColor = Color(0xFFE5E5EA)
                        )
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}
