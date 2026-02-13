package com.velotravel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.velotravel.health.HealthConnectManager
import com.velotravel.ui.achievements.AchievementsScreen
import com.velotravel.ui.achievements.AchievementsViewModel
import com.velotravel.ui.home.HomeScreen
import com.velotravel.ui.home.HomeViewModel
import com.velotravel.ui.theme.VeloTravelTheme
import com.velotravel.ui.update.CheckingUpdateDialog
import com.velotravel.ui.update.NoUpdateDialog
import com.velotravel.ui.update.UpdateAvailableDialog
import com.velotravel.update.UpdateChecker
import com.velotravel.update.UpdateInfo
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var updateChecker: UpdateChecker
    
    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { granted ->
        // Permissions result handled
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val app = application as VeloTravelApp
        val repository = app.repository
        updateChecker = UpdateChecker(this)
        
        // Request Health Connect permissions
        requestHealthPermissions()
        
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
                
                TravelJourneysNavigation(
                    homeViewModel = HomeViewModel(application, repository),
                    achievementsViewModel = AchievementsViewModel(repository)
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
    
    private fun requestHealthPermissions() {
        if (HealthConnectClient.getSdkStatus(this) == HealthConnectClient.SDK_AVAILABLE) {
            lifecycleScope.launch {
                try {
                    val healthConnectClient = HealthConnectClient.getOrCreate(this@MainActivity)
                    val granted = healthConnectClient.permissionController.getGrantedPermissions()
                    
                    if (!HealthConnectManager.PERMISSIONS.all { it in granted }) {
                        val permissionContract = PermissionController.createRequestPermissionResultContract()
                        val intent = permissionContract.createIntent(
                            this@MainActivity,
                            HealthConnectManager.PERMISSIONS
                        )
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
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
    object Home : Screen("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Achievements : Screen("achievements", "Achievements", Icons.Filled.Star, Icons.Outlined.Star)
}

@Composable
fun TravelJourneysNavigation(
    homeViewModel: HomeViewModel,
    achievementsViewModel: AchievementsViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomNavItems = listOf(
        Screen.Home,
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
                    onSelectRoute = { },
                    onViewHistory = { },
                    onViewAchievements = { navController.navigate(Screen.Achievements.route) }
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
