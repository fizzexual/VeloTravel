package com.velotravel.ui.current

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velotravel.data.Routes
import com.velotravel.data.model.Route
import com.velotravel.data.repository.VeloRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class CurrentRouteViewModel(
    private val repository: VeloRepository
) : ViewModel() {
    
    private val _currentRoute = MutableStateFlow<Route?>(null)
    val currentRoute: StateFlow<Route?> = _currentRoute
    
    private val _progressKm = MutableStateFlow(0.0)
    val progressKm: StateFlow<Double> = _progressKm
    
    init {
        loadCurrentRoute()
    }
    
    private fun loadCurrentRoute() {
        viewModelScope.launch {
            repository.getCurrentActiveRoute().collect { progress ->
                progress?.let {
                    _currentRoute.value = Routes.getById(it.routeId)
                    _progressKm.value = it.totalKmCompleted
                }
            }
        }
    }
}

data class CityCoordinate(
    val name: String,
    val x: Float,  // Normalized 0-1 (west to east)
    val y: Float   // Normalized 0-1 (north to south)
)

// Approximate Bulgarian city coordinates (normalized)
val bulgarianCities = mapOf(
    "София" to CityCoordinate("София", 0.35f, 0.55f),
    "София Център" to CityCoordinate("София Център", 0.35f, 0.55f),
    "Пловдив" to CityCoordinate("Пловдив", 0.50f, 0.60f),
    "Варна" to CityCoordinate("Варна", 0.85f, 0.25f),
    "Бургас" to CityCoordinate("Бургас", 0.82f, 0.65f),
    "Русе" to CityCoordinate("Русе", 0.70f, 0.10f),
    "Велико Търново" to CityCoordinate("Велико Търново", 0.60f, 0.30f),
    "В. Търново" to CityCoordinate("В. Търново", 0.60f, 0.30f),
    "Стара Загора" to CityCoordinate("Стара Загора", 0.62f, 0.58f),
    "Плевен" to CityCoordinate("Плевен", 0.50f, 0.25f),
    "Сливен" to CityCoordinate("Сливен", 0.70f, 0.60f),
    "Добрич" to CityCoordinate("Добрич", 0.82f, 0.18f),
    "Шумен" to CityCoordinate("Шумен", 0.75f, 0.28f),
    "Перник" to CityCoordinate("Перник", 0.32f, 0.58f),
    "Хасково" to CityCoordinate("Хасково", 0.60f, 0.75f),
    "Пазарджик" to CityCoordinate("Пазарджик", 0.42f, 0.60f),
    "Ямбол" to CityCoordinate("Ямбол", 0.72f, 0.68f),
    "Благоевград" to CityCoordinate("Благоевград", 0.25f, 0.70f),
    "Враца" to CityCoordinate("Враца", 0.38f, 0.28f),
    "Габрово" to CityCoordinate("Габрово", 0.55f, 0.38f),
    "Асеновград" to CityCoordinate("Асеновград", 0.50f, 0.65f),
    "Видин" to CityCoordinate("Видин", 0.15f, 0.22f),
    "Казанлък" to CityCoordinate("Казанлък", 0.58f, 0.52f),
    "Кюстендил" to CityCoordinate("Кюстендил", 0.28f, 0.62f),
    "Кърджали" to CityCoordinate("Кърджали", 0.58f, 0.80f),
    "Монтана" to CityCoordinate("Монтана", 0.32f, 0.25f),
    "Търговище" to CityCoordinate("Търговище", 0.68f, 0.28f),
    "Силистра" to CityCoordinate("Силистра", 0.82f, 0.08f),
    "Смолян" to CityCoordinate("Смолян", 0.48f, 0.78f),
    "Банско" to CityCoordinate("Банско", 0.28f, 0.72f),
    "Златни пясъци" to CityCoordinate("Златни пясъци", 0.86f, 0.22f),
    "Созопол" to CityCoordinate("Созопол", 0.82f, 0.70f),
    "Несебър" to CityCoordinate("Несебър", 0.84f, 0.63f),
    "Балчик" to CityCoordinate("Балчик", 0.88f, 0.18f),
    "Копривщица" to CityCoordinate("Копривщица", 0.45f, 0.52f),
    "Мелник" to CityCoordinate("Мелник", 0.22f, 0.73f),
    "Белоградчик" to CityCoordinate("Белоградчик", 0.28f, 0.22f),
    "Трявна" to CityCoordinate("Трявна", 0.56f, 0.38f),
    "Бояна" to CityCoordinate("Бояна", 0.34f, 0.57f),
    "Драгалевци" to CityCoordinate("Драгалевци", 0.35f, 0.58f),
    "Арбанаси" to CityCoordinate("Арбанаси", 0.60f, 0.28f),
    "Добринище" to CityCoordinate("Добринище", 0.27f, 0.73f),
    "Шипка" to CityCoordinate("Шипка", 0.58f, 0.48f),
    "Роженски манастир" to CityCoordinate("Роженски манастир", 0.22f, 0.74f),
    "Слънчев бряг" to CityCoordinate("Слънчев бряг", 0.84f, 0.62f),
    "Каварна" to CityCoordinate("Каварна", 0.90f, 0.16f),
    "Пампорово" to CityCoordinate("Пампорово", 0.48f, 0.76f),
    "Сандански" to CityCoordinate("Сандански", 0.24f, 0.72f),
    "Димитровград" to CityCoordinate("Димитровград", 0.58f, 0.68f),
    "Елхово" to CityCoordinate("Елхово", 0.75f, 0.70f),
    "Дупница" to CityCoordinate("Дупница", 0.30f, 0.60f),
    "Самоков" to CityCoordinate("Самоков", 0.38f, 0.58f),
    "Бачково" to CityCoordinate("Бачково", 0.50f, 0.68f),
    "Приморско" to CityCoordinate("Приморско", 0.80f, 0.72f),
    "Разлог" to CityCoordinate("Разлог", 0.27f, 0.72f),
    "Лом" to CityCoordinate("Лом", 0.22f, 0.20f),
    "Ихтиман" to CityCoordinate("Ихтиман", 0.40f, 0.57f),
    "Нови Искър" to CityCoordinate("Нови Искър", 0.37f, 0.54f),
    "Иваново" to CityCoordinate("Иваново", 0.72f, 0.08f),
    "Малко Търново" to CityCoordinate("Малко Търново", 0.78f, 0.75f),
    "Царево" to CityCoordinate("Царево", 0.80f, 0.74f),
    "Китен" to CityCoordinate("Китен", 0.81f, 0.71f),
    "Дряново" to CityCoordinate("Дряново", 0.57f, 0.34f),
    "Боснек" to CityCoordinate("Боснек", 0.32f, 0.58f),
    "Гурково" to CityCoordinate("Гурково", 0.60f, 0.55f),
    "Тутракан" to CityCoordinate("Тутракан", 0.76f, 0.09f),
    "Бяла" to CityCoordinate("Бяла", 0.62f, 0.18f),
    "Бяла Черква" to CityCoordinate("Бяла Черква", 0.66f, 0.14f),
    "Девня" to CityCoordinate("Девня", 0.83f, 0.27f),
    "Албена" to CityCoordinate("Албена", 0.87f, 0.20f),
    "Обзор" to CityCoordinate("Обзор", 0.84f, 0.50f),
    "Разград" to CityCoordinate("Разград", 0.68f, 0.22f),
    "Карнобат" to CityCoordinate("Карнобат", 0.75f, 0.64f),
    "Ябланица" to CityCoordinate("Ябланица", 0.45f, 0.40f),
    "Севлиево" to CityCoordinate("Севлиево", 0.56f, 0.35f),
    "Карлово" to CityCoordinate("Карлово", 0.50f, 0.52f),
    "Калофер" to CityCoordinate("Калофер", 0.52f, 0.52f),
    "Мездра" to CityCoordinate("Мездра", 0.40f, 0.32f),
    "Антон" to CityCoordinate("Антон", 0.43f, 0.54f),
    "Чепеларе" to CityCoordinate("Чепеларе", 0.49f, 0.74f),
    "Околности" to CityCoordinate("Околности", 0.46f, 0.53f),
    "Скалите" to CityCoordinate("Скалите", 0.29f, 0.23f),
    "Копривщица Център" to CityCoordinate("Копривщица Център", 0.45f, 0.52f)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentRouteScreen(
    viewModel: CurrentRouteViewModel
) {
    val currentRoute by viewModel.currentRoute.collectAsState()
    val progressKm by viewModel.progressKm.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Текущ маршрут") }
            )
        }
    ) { padding ->
        if (currentRoute == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Няма активен маршрут",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Изберете маршрут от раздел 'Маршрути'",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Route info card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = currentRoute!!.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        val progressPercent = (progressKm / currentRoute!!.totalKm * 100).toInt()
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Изминати: ${progressKm.toInt()} км",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "$progressPercent%",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = (progressKm / currentRoute!!.totalKm).toFloat(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Остават: ${(currentRoute!!.totalKm - progressKm).toInt()} км",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                }
                
                // Map visualization
                Text(
                    text = "Визуализация на маршрута",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                
                BulgarianMapVisualization(
                    route = currentRoute!!,
                    progressKm = progressKm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp)
                )
                
                // Milestones list
                Text(
                    text = "Етапи на маршрута",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                
                currentRoute!!.milestones.forEach { milestone ->
                    val isPassed = progressKm >= milestone.kmFromStart
                    val isCurrent = progressKm >= milestone.kmFromStart && 
                                   (currentRoute!!.milestones.indexOf(milestone) == currentRoute!!.milestones.lastIndex ||
                                    progressKm < currentRoute!!.milestones[currentRoute!!.milestones.indexOf(milestone) + 1].kmFromStart)
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                isCurrent -> MaterialTheme.colorScheme.secondaryContainer
                                isPassed -> MaterialTheme.colorScheme.tertiaryContainer
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = milestone.cityName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
                                )
                                if (milestone.description.isNotEmpty()) {
                                    Text(
                                        text = milestone.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                            
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${milestone.kmFromStart.toInt()} км",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                if (isCurrent) {
                                    Text(
                                        text = "← Тук сте",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                } else if (isPassed) {
                                    Text(
                                        text = "✓ Минато",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BulgarianMapVisualization(
    route: Route,
    progressKm: Double,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val surfaceColor = MaterialTheme.colorScheme.surface
    
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceColor)
                .padding(24.dp)
        ) {
            val width = size.width
            val height = size.height
            
            // Draw Bulgaria outline (simplified)
            val bulgariaPath = Path().apply {
                // Simplified Bulgaria border
                moveTo(width * 0.15f, height * 0.25f)
                lineTo(width * 0.25f, height * 0.15f)
                lineTo(width * 0.50f, height * 0.08f)
                lineTo(width * 0.75f, height * 0.08f)
                lineTo(width * 0.90f, height * 0.15f)
                lineTo(width * 0.92f, height * 0.30f)
                lineTo(width * 0.88f, height * 0.50f)
                lineTo(width * 0.85f, height * 0.70f)
                lineTo(width * 0.78f, height * 0.78f)
                lineTo(width * 0.60f, height * 0.82f)
                lineTo(width * 0.40f, height * 0.78f)
                lineTo(width * 0.25f, height * 0.72f)
                lineTo(width * 0.20f, height * 0.60f)
                lineTo(width * 0.18f, height * 0.40f)
                close()
            }
            
            drawPath(
                path = bulgariaPath,
                color = Color.LightGray.copy(alpha = 0.3f),
                style = Stroke(width = 2f)
            )
            
            // Get route coordinates
            val routeCoordinates = route.milestones.mapNotNull { milestone ->
                bulgarianCities[milestone.cityName]?.let { coord ->
                    Pair(milestone, coord)
                }
            }
            
            if (routeCoordinates.isNotEmpty()) {
                // Draw route path
                val routePath = Path()
                routeCoordinates.forEachIndexed { index, (_, coord) ->
                    val x = width * coord.x
                    val y = height * coord.y
                    
                    if (index == 0) {
                        routePath.moveTo(x, y)
                    } else {
                        routePath.lineTo(x, y)
                    }
                }
                
                // Draw completed path
                val completedPercent = (progressKm / route.totalKm).toFloat()
                drawPath(
                    path = routePath,
                    color = tertiaryColor.copy(alpha = 0.4f),
                    style = Stroke(
                        width = 6f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    )
                )
                
                // Draw cities and markers
                routeCoordinates.forEachIndexed { index, (milestone, coord) ->
                    val x = width * coord.x
                    val y = height * coord.y
                    val isPassed = progressKm >= milestone.kmFromStart
                    val isStart = index == 0
                    val isEnd = index == routeCoordinates.lastIndex
                    
                    // Draw city circle
                    drawCircle(
                        color = when {
                            isStart -> secondaryColor
                            isEnd -> primaryColor
                            isPassed -> tertiaryColor
                            else -> Color.Gray
                        },
                        radius = if (isStart || isEnd) 12f else 8f,
                        center = Offset(x, y)
                    )
                    
                    // Draw city name
                    drawCircle(
                        color = Color.White,
                        radius = if (isStart || isEnd) 10f else 6f,
                        center = Offset(x, y)
                    )
                }
                
                // Draw current position (biker)
                val currentMilestoneIndex = route.milestones.indexOfLast { it.kmFromStart <= progressKm }
                if (currentMilestoneIndex >= 0 && currentMilestoneIndex < routeCoordinates.size - 1) {
                    val currentMilestone = routeCoordinates[currentMilestoneIndex]
                    val nextMilestone = routeCoordinates[currentMilestoneIndex + 1]
                    
                    val segmentStart = route.milestones[currentMilestoneIndex].kmFromStart
                    val segmentEnd = route.milestones[currentMilestoneIndex + 1].kmFromStart
                    val segmentProgress = ((progressKm - segmentStart) / (segmentEnd - segmentStart)).toFloat()
                    
                    val bikerX = currentMilestone.second.x + (nextMilestone.second.x - currentMilestone.second.x) * segmentProgress
                    val bikerY = currentMilestone.second.y + (nextMilestone.second.y - currentMilestone.second.y) * segmentProgress
                    
                    // Draw biker icon (simplified)
                    val bikerCenterX = width * bikerX
                    val bikerCenterY = height * bikerY
                    
                    // Biker body
                    drawCircle(
                        color = primaryColor,
                        radius = 16f,
                        center = Offset(bikerCenterX, bikerCenterY)
                    )
                    
                    // Biker head
                    drawCircle(
                        color = Color.White,
                        radius = 6f,
                        center = Offset(bikerCenterX, bikerCenterY - 8f)
                    )
                    
                    // Biker wheels (simplified)
                    drawCircle(
                        color = Color.White,
                        radius = 4f,
                        center = Offset(bikerCenterX - 8f, bikerCenterY + 8f)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 4f,
                        center = Offset(bikerCenterX + 8f, bikerCenterY + 8f)
                    )
                }
            }
        }
    }
}
