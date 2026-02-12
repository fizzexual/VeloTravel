package com.velotravel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.velotravel.ui.achievements.AchievementsScreen
import com.velotravel.ui.achievements.AchievementsViewModel
import com.velotravel.ui.current.CurrentRouteScreen
import com.velotravel.ui.current.CurrentRouteViewModel
import com.velotravel.ui.history.HistoryScreen
import com.velotravel.ui.history.HistoryViewModel
import com.velotravel.ui.home.HomeScreen
import com.velotravel.ui.home.HomeViewModel
import com.velotravel.ui.routes.RouteSelectionScreen
import com.velotravel.ui.routes.RouteSelectionViewModel
import com.velotravel.ui.theme.VeloTravelTheme
import com.velotravel.ui.update.CheckingUpdateDialog
import com.velotravel.ui.update.NoUpdateDialog
import com.velotravel.ui.update.UpdateAvailableDialog
import com.velotravel.update.UpdateChecker
import com.velotravel.update.UpdateInfo
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var updateChecker: UpdateChecker
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val app = application as VeloTravelApp
        val repository = app.repository
        updateChecker = UpdateChecker(this)
        
        setContent {
            VeloTravelTheme {
                var updateInfo by remember { mutableStateOf<UpdateInfo?>(null) }
                var showUpdateDialog by remember { mutableStateOf(false) }
                var showCheckingDialog by remember { mutableStateOf(false) }
                var showNoUpdateDialog by remember { mutableStateOf(false) }
                
                // Check for updates on app start
                LaunchedEffect(Unit) {
                    lifecycleScope.launch {
                        showCheckingDialog = true
                        val update = updateChecker.checkForUpdates()
                        showCheckingDialog = false
                        
                        if (update != null) {
                            updateInfo = update
                            showUpdateDialog = true
                        }
                    }
                }
                
                VeloTravelNavigation(
                    homeViewModel = HomeViewModel(repository),
                    currentRouteViewModel = CurrentRouteViewModel(repository),
                    routeSelectionViewModel = RouteSelectionViewModel(repository),
                    historyViewModel = HistoryViewModel(repository),
                    achievementsViewModel = AchievementsViewModel(repository),
                    onCheckForUpdates = {
                        lifecycleScope.launch {
                            showCheckingDialog = true
                            val update = updateChecker.checkForUpdates()
                            showCheckingDialog = false
                            
                            if (update != null) {
                                updateInfo = update
                                showUpdateDialog = true
                            } else {
                                showNoUpdateDialog = true
                            }
                        }
                    }
                )
                
                // Update dialogs
                if (showCheckingDialog) {
                    CheckingUpdateDialog(
                        onDismiss = { showCheckingDialog = false }
                    )
                }
                
                if (showUpdateDialog && updateInfo != null) {
                    UpdateAvailableDialog(
                        updateInfo = updateInfo!!,
                        onDownload = {
                            updateChecker.downloadAndInstallUpdate(updateInfo!!)
                            showUpdateDialog = false
                        },
                        onDismiss = { showUpdateDialog = false }
                    )
                }
                
                if (showNoUpdateDialog) {
                    NoUpdateDialog(
                        onDismiss = { showNoUpdateDialog = false }
                    )
                }
            }
        }
    }
}

sealed class Screen(
    val route: String,
    val title: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector
) {
    object Home : Screen("home", "Начало", Icons.Filled.Home, Icons.Outlined.Home)
    object Current : Screen("current", "Текущ", Icons.Filled.LocationOn, Icons.Outlined.LocationOn)
    object Routes : Screen("routes", "Маршрути", Icons.Filled.List, Icons.Outlined.List)
    object Achievements : Screen("achievements", "Постижения", Icons.Filled.Star, Icons.Outlined.Star)
}

@Composable
fun VeloTravelNavigation(
    homeViewModel: HomeViewModel,
    currentRouteViewModel: CurrentRouteViewModel,
    routeSelectionViewModel: RouteSelectionViewModel,
    historyViewModel: HistoryViewModel,
    achievementsViewModel: AchievementsViewModel,
    onCheckForUpdates: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Current,
        Screen.Routes,
        Screen.Achievements
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selected) screen.iconSelected else screen.iconUnselected,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onSelectRoute = { navController.navigate(Screen.Routes.route) },
                    onViewHistory = { navController.navigate("history") },
                    onViewAchievements = { navController.navigate(Screen.Achievements.route) }
                )
            }
            
            composable(Screen.Current.route) {
                CurrentRouteScreen(
                    viewModel = currentRouteViewModel
                )
            }
            
            composable(Screen.Routes.route) {
                RouteSelectionScreen(
                    viewModel = routeSelectionViewModel,
                    onBack = { navController.navigate(Screen.Home.route) },
                    onRouteSelected = { navController.navigate(Screen.Home.route) }
                )
            }
            
            composable("history") {
                HistoryScreen(
                    viewModel = historyViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            
            composable(Screen.Achievements.route) {
                AchievementsScreen(
                    viewModel = achievementsViewModel,
                    onBack = { navController.navigate(Screen.Home.route) }
                )
            }
        }
    }
}
