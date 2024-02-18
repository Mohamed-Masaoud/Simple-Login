package com.example.simpleregister.Screens

import MainScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Define navigation destinations
object Destinations {
    const val MainScreen = "main"
    const val SearchScreen = "search"
    const val FavoriteScreen = "favorite"
}

@Composable
fun CustomBottomNavigationBar(navController: NavHostController) {
    // Use a built-in theme color for the background
    val backgroundColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor), // Apply the built-in theme color
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home button on the left
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            IconButton(onClick = { navController.navigate(Destinations.MainScreen) }) {
                Icon(Icons.Filled.Home, contentDescription = "Main", tint = Color.White)
            }
        }
        // Search button in the middle
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            IconButton(onClick = { navController.navigate(Destinations.SearchScreen) }) {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
            }
        }
        // Favorites button on the right
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            IconButton(onClick = { navController.navigate(Destinations.FavoriteScreen) }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorites", tint = Color.White)
            }
        }
    }
}
@Composable
fun AppWithCustomNavigationBar() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { CustomBottomNavigationBar(navController) }
    ) { innerPadding ->
        // Use the provided innerPadding to pad the content accordingly
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Destinations.MainScreen) {
                composable(Destinations.MainScreen) { MainScreen() }
                composable(Destinations.SearchScreen) { SearchScreen() }
                composable(Destinations.FavoriteScreen) { FavoriteScreen() }
            }
        }
    }
}


