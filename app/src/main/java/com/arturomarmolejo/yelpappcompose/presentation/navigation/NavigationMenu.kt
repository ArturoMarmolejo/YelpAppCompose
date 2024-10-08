package com.arturomarmolejo.yelpappcompose.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun VerticalNavigationBar(navHostController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentRoute(navHostController) == "HomeScreen",
            onClick = { navHostController.navigate("MapScreen") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Map") },
            selected = currentRoute(navHostController) == "MapScreen",
            onClick = { navHostController.navigate("MapScreen") }
        )
    }
}
@Composable
fun VerticalNavigationRail(navHostController: NavHostController) {
   NavigationRail() {
        NavigationRailItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentRoute(navHostController) == "HomeScreen",
            onClick = { navHostController.navigate("HomeScreen")}
        )
        NavigationRailItem(
            icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Map") },
            selected = currentRoute(navHostController) == "MapScreen",
            onClick = { navHostController.navigate("MapScreen")}
        )
    }
}

@Composable
fun VerticalNavigationDrawer(navHostController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column {
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentRoute(navHostController) == "HomeScreen",
                    onClick = {
                        navHostController.navigate("HomeScreen")
                        scope.launch { drawerState.close() } // Close drawer after navigation
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Map") },
                    label = { Text("Map") },
                    selected = currentRoute(navHostController) == "MapScreen",
                    onClick = {
                        navHostController.navigate("MapScreen")
                        scope.launch { drawerState.close() } // Close drawer after navigation
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) { }
}

@Composable
fun currentRoute(navHostController: NavHostController): String? {
    return navHostController.currentBackStackEntry?.destination?.route
}