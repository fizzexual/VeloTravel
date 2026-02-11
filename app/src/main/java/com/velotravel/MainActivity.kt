package com.velotravel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.velotravel.ui.achievements.AchievementsScreen
import com.velotravel.ui.achievements.AchievementsViewModel
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
                    routeSelectionViewModel = RouteSelectionViewModel(repository),
                    historyViewModel = HistoryViewModel(repository),
                    achievementsViewModel = AchievementsViewModel(repository)
                )
            }
        }
    }
}

@Composable
fun VeloTravelNavigation(
    homeViewModel: HomeViewModel,
    routeSelectionViewModel: RouteSelectionViewModel,
    historyViewModel: HistoryViewModel,
    achievementsViewModel: AchievementsViewModel
) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = homeViewModel,
                onSelectRoute = { navController.navigate("routes") },
                onViewHistory = { navController.navigate("history") },
                onViewAchievements = { navController.navigate("achievements") }
            )
        }
        
        composable("routes") {
            RouteSelectionScreen(
                viewModel = routeSelectionViewModel,
                onBack = { navController.popBackStack() },
                onRouteSelected = { navController.popBackStack() }
            )
        }
        
        composable("history") {
            HistoryScreen(
                viewModel = historyViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable("achievements") {
            AchievementsScreen(
                viewModel = achievementsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
