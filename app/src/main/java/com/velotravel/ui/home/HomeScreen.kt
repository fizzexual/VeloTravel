package com.velotravel.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSelectRoute: () -> Unit,
    onViewHistory: () -> Unit,
    onViewAchievements: () -> Unit
) {
    val currentRoute by viewModel.currentRoute.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val kmInput by viewModel.kmInput.collectAsState()
    val showSuccess by viewModel.showSuccess.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Велопътешественик") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentRoute == null) {
                // Няма активен маршрут
                NoRouteContent(onSelectRoute)
            } else {
                // Има активен маршрут
                ActiveRouteContent(
                    viewModel = viewModel,
                    currentRoute = currentRoute!!,
                    progress = progress,
                    kmInput = kmInput,
                    showSuccess = showSuccess,
                    onViewHistory = onViewHistory,
                    onViewAchievements = onViewAchievements
                )
            }
        }
    }
}

@Composable
private fun NoRouteContent(onSelectRoute: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚴",
            fontSize = 72.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Добре дошли!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Изберете маршрут, за да започнете\nвашето виртуално пътуване",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onSelectRoute,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Избери маршрут", fontSize = 18.sp)
        }
    }
}

@Composable
private fun ActiveRouteContent(
    viewModel: HomeViewModel,
    currentRoute: com.velotravel.data.model.Route,
    progress: com.velotravel.data.model.UserProgress?,
    kmInput: String,
    showSuccess: Boolean,
    onViewHistory: () -> Unit,
    onViewAchievements: () -> Unit
) {
    // Текущ напредък
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentRoute.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Текущо: ${viewModel.getCurrentMilestone()}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress bar
            LinearProgressIndicator(
                progress = { viewModel.getProgressPercentage() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${String.format("%.1f", progress?.totalKmCompleted ?: 0.0)} км",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${currentRoute.totalKm} км",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            }
            
            viewModel.getNextMilestone()?.let { nextCity ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Следващ: $nextCity",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
    
    Spacer(modifier = Modifier.height(24.dp))
    
    // Въвеждане на километри
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Добави днешни километри",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = kmInput,
                onValueChange = { viewModel.onKmInputChange(it) },
                label = { Text("Километри") },
                placeholder = { Text("0.0") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                suffix = { Text("км") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { viewModel.addKilometers() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = kmInput.toDoubleOrNull()?.let { it > 0 } == true
            ) {
                Text("Добави", fontSize = 16.sp)
            }
            
            AnimatedVisibility(
                visible = showSuccess,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Text(
                        text = "✓ Браво! Километрите са добавени",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
    }
    
    Spacer(modifier = Modifier.height(16.dp))
    
    // Бутони за навигация
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onViewHistory,
            modifier = Modifier.weight(1f)
        ) {
            Text("История")
        }
        OutlinedButton(
            onClick = onViewAchievements,
            modifier = Modifier.weight(1f)
        ) {
            Text("Постижения")
        }
    }
    
    // Мотивационно съобщение
    if (progress?.totalKmCompleted ?: 0.0 > 0) {
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = getMotivationalMessage(progress?.totalKmCompleted ?: 0.0),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}

private fun getMotivationalMessage(totalKm: Double): String {
    return when {
        totalKm < 10 -> "Всяко пътуване започва с първата крачка. Продължавай! 🌟"
        totalKm < 50 -> "Страхотен напредък! Всеки километър е победа. 💪"
        totalKm < 100 -> "Вървиш чудесно! Пътят е дълъг, но ти го правиш. 🚴"
        totalKm < 200 -> "Половината е зад теб! Продължавай в същия темп. ⭐"
        else -> "Невероятно постижение! Ти си истински пътешественик! 🎉"
    }
}
