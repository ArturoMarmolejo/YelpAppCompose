package com.arturomarmolejo.yelpappcompose.presentation.navigation

sealed class Routes(val route: String) {
    object HomeScreen: Routes("HomeScreen")
    object DetailsScreen: Routes("DetailsScreen/{item}")
}