package com.example.matchmate

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.matchmate.historyscreen.HistoryScreen
import com.example.matchmate.myprofilescreen.MyProfileScreen

import com.example.matchmate.matchscreen.HomeScreen
import com.example.matchmate.ui.theme.MatchMateTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector) {
    object Home : NavDestination(title = "Home", route = "home_screen", icon = Icons.Filled.Home)
    object History :
        NavDestination(title = "History", route = "history_screen", icon = Icons.Filled.DateRange)

    object Profile : NavDestination(title = "Profile", route = "profile_screen", icon = Icons.Filled.AccountBox)

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Log.e("Location", "Permission Denied")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        //installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkAndRequestLocationPermission()


        setContent {

            val navController = rememberNavController()
            val items = listOf(
                NavDestination.Home, NavDestination.History, NavDestination.Profile
            )
            var selectedIndex by remember { mutableIntStateOf(0) }

            MatchMateTheme {
                //val scrollState = rememberScrollState() // 1. Remember scroll state

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.White
                        ) {
                            items.forEachIndexed { index, screen ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(imageVector = screen.icon, contentDescription = null, modifier = Modifier.size(30.dp))
                                    },
                                    label = { Text(screen.title) },
                                    selected = index == selectedIndex,
                                    onClick = {
                                        selectedIndex = index
                                        navController.navigate(screen.route) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color(0xE32443C0),
                                        selectedTextColor = Color(0xE32443C0),
                                        indicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavigationHost(
                        navController = navController,
                        innerPadding = innerPadding,
                    )
                }

                /*Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Optional: Add some padding around the content
                    ) {

                    MyListScreen()

                }*/
            }
        }
    }

    @Composable
    private fun NavigationHost(
        navController: NavHostController,
        innerPadding: PaddingValues
    ) {
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = Modifier
                .background(color = Color.White)
                .padding(innerPadding)
        ) {
            composable(route = NavDestination.Home.route) {
                HomeScreen()
            }
            composable(route = NavDestination.History.route) {
                HistoryScreen()
            }
            composable(route = NavDestination.Profile.route) {
                MyProfileScreen(
                )
            }
        }
    }


    private fun checkAndRequestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("Location", "Lat: $latitude, Lon: $longitude")
                } else {
                    Log.e("Location", "Location is null")
                }
            }
            .addOnFailureListener {
                Log.e("Location", "Error getting location", it)
            }
    }
}
