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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val app = application as VeloTravelApp
        val repository = app.repository
        
        setContent {
            VeloTravelTheme {
                VeloTravelNavigation(
                    homeViewModel = HomeViewModel(repository),
                    currentRouteViewModel = CurrentRouteViewModel(repository),
                    routeSelectionViewModel = RouteSelectionViewModel(repository),
                    historyViewModel = HistoryViewModel(repository),
                    achievementsViewModel = AchievementsViewModel(repository)
                )
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
    achievementsViewModel: AchievementsViewModel
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
