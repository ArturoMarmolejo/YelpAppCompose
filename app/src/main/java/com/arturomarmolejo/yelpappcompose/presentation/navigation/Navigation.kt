package com.arturomarmolejo.yelpappcompose.presentation.navigation

import android.content.Context
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arturomarmolejo.yelpappcompose.presentation.viewmodel.YelpViewModel
import com.arturomarmolejo.yelpappcompose.presentation.views.DetailsScreen
import com.arturomarmolejo.yelpappcompose.presentation.views.HomeScreen
import com.arturomarmolejo.yelpappcompose.presentation.views.MapScreen

@Composable
fun Navigation(
    context: Context,
    location: Location?,
    yelpViewModel: YelpViewModel,
    navHostController: NavHostController
) {
    NavHost(navHostController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(context, navHostController, yelpViewModel)
        }
        composable("DetailsScreen/{itemId}") { backStackEntry ->
           DetailsScreen(navBackStackEntry = backStackEntry, yelpViewModel = yelpViewModel)
        }
        composable("MapScreen") {
            //TO-DO: MapScreen
            MapScreen(yelpViewModel, location)
        }
    }
}