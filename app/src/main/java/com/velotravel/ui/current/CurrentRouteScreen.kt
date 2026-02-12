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
    val y: Float,  // Normalized 0-1 (north to south)
    val region: String
)

// Bulgarian regions (oblasti)
enum class BulgarianRegion(val displayName: String) {
    BLAGOEVGRAD("Благоевград"),
    BURGAS("Бургас"),
    VARNA("Варна"),
    VELIKO_TARNOVO("Велико Търново"),
    VIDIN("Видин"),
    VRATSA("Враца"),
    GABROVO("Габрово"),
    DOBRICH("Добрич"),
    KARDZHALI("Кърджали"),
    KYUSTENDIL("Кюстендил"),
    LOVECH("Ловеч"),
    MONTANA("Монтана"),
    PAZARDZHIK("Пазарджик"),
    PERNIK("Перник"),
    PLEVEN("Плевен"),
    PLOVDIV("Пловдив"),
    RAZGRAD("Разград"),
    RUSE("Русе"),
    SILISTRA("Силистра"),
    SLIVEN("Сливен"),
    SMOLYAN("Смолян"),
    SOFIA_CITY("София-град"),
    SOFIA("София"),
    STARA_ZAGORA("Стара Загора"),
    TARGOVISHTE("Търговище"),
    HASKOVO("Хасково"),
    SHUMEN("Шумен"),
    YAMBOL("Ямбол")
}

// Bulgarian city coordinates with regions
val bulgarianCities = mapOf(
    "София" to CityCoordinate("София", 0.35f, 0.55f, "София-град"),
    "София Център" to CityCoordinate("София Център", 0.35f, 0.55f, "София-град"),
    "Пловдив" to CityCoordinate("Пловдив", 0.50f, 0.60f, "Пловдив"),
    "Варна" to CityCoordinate("Варна", 0.85f, 0.25f, "Варна"),
    "Бургас" to CityCoordinate("Бургас", 0.82f, 0.65f, "Бургас"),
    "Русе" to CityCoordinate("Русе", 0.70f, 0.10f, "Русе"),
    "Велико Търново" to CityCoordinate("Велико Търново", 0.60f, 0.30f, "Велико Търново"),
    "В. Търново" to CityCoordinate("В. Търново", 0.60f, 0.30f, "Велико Търново"),
    "Стара Загора" to CityCoordinate("Стара Загора", 0.62f, 0.58f, "Стара Загора"),
    "Плевен" to CityCoordinate("Плевен", 0.50f, 0.25f, "Плевен"),
    "Сливен" to CityCoordinate("Сливен", 0.70f, 0.60f, "Сливен"),
    "Добрич" to CityCoordinate("Добрич", 0.82f, 0.18f, "Добрич"),
    "Шумен" to CityCoordinate("Шумен", 0.75f, 0.28f, "Шумен"),
    "Перник" to CityCoordinate("Перник", 0.32f, 0.58f, "Перник"),
    "Хасково" to CityCoordinate("Хасково", 0.60f, 0.75f, "Хасково"),
    "Пазарджик" to CityCoordinate("Пазарджик", 0.42f, 0.60f, "Пазарджик"),
    "Ямбол" to CityCoordinate("Ямбол", 0.72f, 0.68f, "Ямбол"),
    "Благоевград" to CityCoordinate("Благоевград", 0.25f, 0.70f, "Благоевград"),
    "Враца" to CityCoordinate("Враца", 0.38f, 0.28f, "Враца"),
    "Габрово" to CityCoordinate("Габрово", 0.55f, 0.38f, "Габрово"),
    "Асеновград" to CityCoordinate("Асеновград", 0.50f, 0.65f, "Пловдив"),
    "Видин" to CityCoordinate("Видин", 0.15f, 0.22f, "Видин"),
    "Казанлък" to CityCoordinate("Казанлък", 0.58f, 0.52f, "Стара Загора"),
    "Кюстендил" to CityCoordinate("Кюстендил", 0.28f, 0.62f, "Кюстендил"),
    "Кърджали" to CityCoordinate("Кърджали", 0.58f, 0.80f, "Кърджали"),
    "Монтана" to CityCoordinate("Монтана", 0.32f, 0.25f, "Монтана"),
    "Търговище" to CityCoordinate("Търговище", 0.68f, 0.28f, "Търговище"),
    "Силистра" to CityCoordinate("Силистра", 0.82f, 0.08f, "Силистра"),
    "Смолян" to CityCoordinate("Смолян", 0.48f, 0.78f, "Смолян"),
    "Банско" to CityCoordinate("Банско", 0.28f, 0.72f, "Благоевград"),
    "Златни пясъци" to CityCoordinate("Златни пясъци", 0.86f, 0.22f, "Варна"),
    "Созопол" to CityCoordinate("Созопол", 0.82f, 0.70f, "Бургас"),
    "Несебър" to CityCoordinate("Несебър", 0.84f, 0.63f, "Бургас"),
    "Балчик" to CityCoordinate("Балчик", 0.88f, 0.18f, "Добрич"),
    "Копривщица" to CityCoordinate("Копривщица", 0.45f, 0.52f, "София"),
    "Мелник" to CityCoordinate("Мелник", 0.22f, 0.73f, "Благоевград"),
    "Белоградчик" to CityCoordinate("Белоградчик", 0.28f, 0.22f, "Видин"),
    "Трявна" to CityCoordinate("Трявна", 0.56f, 0.38f, "Габрово"),
    "Бояна" to CityCoordinate("Бояна", 0.34f, 0.57f, "София-град"),
    "Драгалевци" to CityCoordinate("Драгалевци", 0.35f, 0.58f, "София-град"),
    "Арбанаси" to CityCoordinate("Арбанаси", 0.60f, 0.28f, "Велико Търново"),
    "Добринище" to CityCoordinate("Добринище", 0.27f, 0.73f, "Благоевград"),
    "Шипка" to CityCoordinate("Шипка", 0.58f, 0.48f, "Стара Загора"),
    "Роженски манастир" to CityCoordinate("Роженски манастир", 0.22f, 0.74f, "Благоевград"),
    "Слънчев бряг" to CityCoordinate("Слънчев бряг", 0.84f, 0.62f, "Бургас"),
    "Каварна" to CityCoordinate("Каварна", 0.90f, 0.16f, "Добрич"),
    "Пампорово" to CityCoordinate("Пампорово", 0.48f, 0.76f, "Смолян"),
    "Сандански" to CityCoordinate("Сандански", 0.24f, 0.72f, "Благоевград"),
    "Димитровград" to CityCoordinate("Димитровград", 0.58f, 0.68f, "Хасково"),
    "Елхово" to CityCoordinate("Елхово", 0.75f, 0.70f, "Ямбол"),
    "Дупница" to CityCoordinate("Дупница", 0.30f, 0.60f, "Кюстендил"),
    "Самоков" to CityCoordinate("Самоков", 0.38f, 0.58f, "София"),
    "Бачково" to CityCoordinate("Бачково", 0.50f, 0.68f, "Пловдив"),
    "Приморско" to CityCoordinate("Приморско", 0.80f, 0.72f, "Бургас"),
    "Разлог" to CityCoordinate("Разлог", 0.27f, 0.72f, "Благоевград"),
    "Лом" to CityCoordinate("Лом", 0.22f, 0.20f, "Монтана"),
    "Ихтиман" to CityCoordinate("Ихтиман", 0.40f, 0.57f, "София"),
    "Нови Искър" to CityCoordinate("Нови Искър", 0.37f, 0.54f, "София"),
    "Иваново" to CityCoordinate("Иваново", 0.72f, 0.08f, "Русе"),
    "Малко Търново" to CityCoordinate("Малко Търново", 0.78f, 0.75f, "Бургас"),
    "Царево" to CityCoordinate("Царево", 0.80f, 0.74f, "Бургас"),
    "Китен" to CityCoordinate("Китен", 0.81f, 0.71f, "Бургас"),
    "Дряново" to CityCoordinate("Дряново", 0.57f, 0.34f, "Габрово"),
    "Боснек" to CityCoordinate("Боснек", 0.32f, 0.58f, "Перник"),
    "Гурково" to CityCoordinate("Гурково", 0.60f, 0.55f, "Стара Загора"),
    "Тутракан" to CityCoordinate("Тутракан", 0.76f, 0.09f, "Силистра"),
    "Бяла" to CityCoordinate("Бяла", 0.62f, 0.18f, "Русе"),
    "Бяла Черква" to CityCoordinate("Бяла Черква", 0.66f, 0.14f, "Русе"),
    "Девня" to CityCoordinate("Девня", 0.83f, 0.27f, "Варна"),
    "Албена" to CityCoordinate("Албена", 0.87f, 0.20f, "Добрич"),
    "Обзор" to CityCoordinate("Обзор", 0.84f, 0.50f, "Бургас"),
    "Разград" to CityCoordinate("Разград", 0.68f, 0.22f, "Разград"),
    "Карнобат" to CityCoordinate("Карнобат", 0.75f, 0.64f, "Бургас"),
    "Ябланица" to CityCoordinate("Ябланица", 0.45f, 0.40f, "Ловеч"),
    "Севлиево" to CityCoordinate("Севлиево", 0.56f, 0.35f, "Габрово"),
    "Карлово" to CityCoordinate("Карлово", 0.50f, 0.52f, "Пловдив"),
    "Калофер" to CityCoordinate("Калофер", 0.52f, 0.52f, "Пловдив"),
    "Мездра" to CityCoordinate("Мездра", 0.40f, 0.32f, "Враца"),
    "Антон" to CityCoordinate("Антон", 0.43f, 0.54f, "София"),
    "Чепеларе" to CityCoordinate("Чепеларе", 0.49f, 0.74f, "Смолян"),
    "Околности" to CityCoordinate("Околности", 0.46f, 0.53f, "София"),
    "Скалите" to CityCoordinate("Скалите", 0.29f, 0.23f, "Видин"),
    "Копривщица Център" to CityCoordinate("Копривщица Център", 0.45f, 0.52f, "София")
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
    val outlineColor = MaterialTheme.colorScheme.outline
    
    // Get start and end coordinates
    val startCity = route.milestones.firstOrNull()?.cityName
    val endCity = route.milestones.lastOrNull()?.cityName
    
    val startCoord = startCity?.let { bulgarianCities[it] }
    val endCoord = endCity?.let { bulgarianCities[it] }
    
    // Check if same region
    val sameRegion = startCoord?.region == endCoord?.region
    
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceColor)
                .padding(16.dp)
        ) {
            val width = size.width
            val height = size.height
            
            // Draw detailed Bulgaria outline with regions
            drawBulgariaWithRegions(width, height, outlineColor)
            
            // Draw route line if coordinates exist
            if (startCoord != null && endCoord != null) {
                val startX = width * startCoord.x
                val startY = height * startCoord.y
                val endX = width * endCoord.x
                val endY = height * endCoord.y
                
                // Draw route line
                drawLine(
                    color = primaryColor,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )
                
                // Draw progress line
                val progressPercent = (progressKm / route.totalKm).toFloat().coerceIn(0f, 1f)
                val currentX = startX + (endX - startX) * progressPercent
                val currentY = startY + (endY - startY) * progressPercent
                
                drawLine(
                    color = tertiaryColor,
                    start = Offset(startX, startY),
                    end = Offset(currentX, currentY),
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )
                
                // Draw start marker
                drawCircle(
                    color = secondaryColor,
                    radius = 16f,
                    center = Offset(startX, startY)
                )
                drawCircle(
                    color = Color.White,
                    radius = 12f,
                    center = Offset(startX, startY)
                )
                
                // Draw end marker
                drawCircle(
                    color = primaryColor,
                    radius = 16f,
                    center = Offset(endX, endY)
                )
                drawCircle(
                    color = Color.White,
                    radius = 12f,
                    center = Offset(endX, endY)
                )
                
                // Draw biker at current position
                drawCircle(
                    color = tertiaryColor,
                    radius = 20f,
                    center = Offset(currentX, currentY)
                )
                
                // Biker icon (simplified bicycle)
                drawCircle(
                    color = Color.White,
                    radius = 8f,
                    center = Offset(currentX, currentY - 6f)
                )
                
                // Wheels
                drawCircle(
                    color = Color.White,
                    radius = 5f,
                    center = Offset(currentX - 10f, currentY + 10f),
                    style = Stroke(width = 2f)
                )
                drawCircle(
                    color = Color.White,
                    radius = 5f,
                    center = Offset(currentX + 10f, currentY + 10f),
                    style = Stroke(width = 2f)
                )
            }
        }
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawBulgariaWithRegions(
    width: Float,
    height: Float,
    color: Color
) {
    // Main Bulgaria outline
    val bulgariaPath = Path().apply {
        moveTo(width * 0.15f, height * 0.22f) // Vidin
        
        // North border (Danube river)
        lineTo(width * 0.22f, height * 0.18f)
        lineTo(width * 0.32f, height * 0.20f)
        lineTo(width * 0.42f, height * 0.18f)
        lineTo(width * 0.52f, height * 0.15f)
        lineTo(width * 0.62f, height * 0.12f)
        lineTo(width * 0.70f, height * 0.08f)
        lineTo(width * 0.78f, height * 0.06f)
        lineTo(width * 0.84f, height * 0.08f)
        
        // Northeast corner
        lineTo(width * 0.88f, height * 0.12f)
        lineTo(width * 0.92f, height * 0.18f)
        
        // East coast (Black Sea)
        lineTo(width * 0.90f, height * 0.24f)
        lineTo(width * 0.88f, height * 0.32f)
        lineTo(width * 0.86f, height * 0.42f)
        lineTo(width * 0.85f, height * 0.52f)
        lineTo(width * 0.84f, height * 0.60f)
        lineTo(width * 0.83f, height * 0.68f)
        lineTo(width * 0.80f, height * 0.74f)
        
        // Southeast border
        lineTo(width * 0.75f, height * 0.76f)
        lineTo(width * 0.68f, height * 0.78f)
        lineTo(width * 0.62f, height * 0.80f)
        lineTo(width * 0.56f, height * 0.82f)
        lineTo(width * 0.50f, height * 0.80f)
        
        // South border
        lineTo(width * 0.44f, height * 0.76f)
        lineTo(width * 0.38f, height * 0.74f)
        lineTo(width * 0.32f, height * 0.72f)
        lineTo(width * 0.26f, height * 0.68f)
        lineTo(width * 0.22f, height * 0.64f)
        
        // West border
        lineTo(width * 0.20f, height * 0.58f)
        lineTo(width * 0.22f, height * 0.52f)
        lineTo(width * 0.24f, height * 0.46f)
        lineTo(width * 0.22f, height * 0.38f)
        lineTo(width * 0.18f, height * 0.30f)
        lineTo(width * 0.15f, height * 0.22f)
        
        close()
    }
    
    // Draw main outline
    drawPath(
        path = bulgariaPath,
        color = color,
        style = Stroke(width = 3f)
    )
    
    // Draw regional boundaries
    // Northwest regions (Vidin, Montana, Vratsa)
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.22f, height * 0.18f),
        end = Offset(width * 0.22f, height * 0.64f),
        strokeWidth = 1.5f
    )
    
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.32f, height * 0.20f),
        end = Offset(width * 0.32f, height * 0.72f),
        strokeWidth = 1.5f
    )
    
    // North-central regions (Pleven, Lovech, Veliko Tarnovo)
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.42f, height * 0.18f),
        end = Offset(width * 0.42f, height * 0.60f),
        strokeWidth = 1.5f
    )
    
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.52f, height * 0.15f),
        end = Offset(width * 0.52f, height * 0.75f),
        strokeWidth = 1.5f
    )
    
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.62f, height * 0.12f),
        end = Offset(width * 0.62f, height * 0.80f),
        strokeWidth = 1.5f
    )
    
    // Northeast regions (Ruse, Razgrad, Targovishte, Shumen, Dobrich)
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.70f, height * 0.08f),
        end = Offset(width * 0.70f, height * 0.60f),
        strokeWidth = 1.5f
    )
    
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.78f, height * 0.06f),
        end = Offset(width * 0.78f, height * 0.75f),
        strokeWidth = 1.5f
    )
    
    // Horizontal divisions
    // North tier
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.15f, height * 0.30f),
        end = Offset(width * 0.88f, height * 0.30f),
        strokeWidth = 1.5f
    )
    
    // Central tier
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.20f, height * 0.45f),
        end = Offset(width * 0.86f, height * 0.45f),
        strokeWidth = 1.5f
    )
    
    // South-central tier
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.22f, height * 0.60f),
        end = Offset(width * 0.84f, height * 0.60f),
        strokeWidth = 1.5f
    )
    
    // South tier
    drawLine(
        color = color.copy(alpha = 0.4f),
        start = Offset(width * 0.26f, height * 0.72f),
        end = Offset(width * 0.75f, height * 0.76f),
        strokeWidth = 1.5f
    )
    
    // Additional diagonal boundaries for more accurate regions
    // Sofia region boundary
    drawLine(
        color = color.copy(alpha = 0.3f),
        start = Offset(width * 0.32f, height * 0.52f),
        end = Offset(width * 0.42f, height * 0.52f),
        strokeWidth = 1f
    )
    
    // Plovdiv region boundary
    drawLine(
        color = color.copy(alpha = 0.3f),
        start = Offset(width * 0.42f, height * 0.55f),
        end = Offset(width * 0.52f, height * 0.55f),
        strokeWidth = 1f
    )
    
    // Stara Zagora region boundary
    drawLine(
        color = color.copy(alpha = 0.3f),
        start = Offset(width * 0.52f, height * 0.52f),
        end = Offset(width * 0.62f, height * 0.52f),
        strokeWidth = 1f
    )
    
    // Burgas region boundary
    drawLine(
        color = color.copy(alpha = 0.3f),
        start = Offset(width * 0.78f, height * 0.60f),
        end = Offset(width * 0.84f, height * 0.68f),
        strokeWidth = 1f
    )
    
    // Varna region boundary
    drawLine(
        color = color.copy(alpha = 0.3f),
        start = Offset(width * 0.78f, height * 0.20f),
        end = Offset(width * 0.88f, height * 0.25f),
        strokeWidth = 1f
    )
}
