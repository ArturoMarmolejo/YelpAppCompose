package com.arturomarmolejo.yelpappcompose.presentation.navigation

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

@Composable
fun Navigation(
    yelpViewModel: YelpViewModel,
    navHostController: NavHostController
) {
    NavHost(navHostController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navHostController, yelpViewModel)
        }
        composable("DetailsScreen/{item}") {
           DetailsScreen()
        }
    }
}