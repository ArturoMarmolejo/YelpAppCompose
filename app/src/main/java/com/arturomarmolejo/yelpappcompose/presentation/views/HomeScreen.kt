package com.arturomarmolejo.yelpappcompose.presentation.views

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Contacts.Intents.UI
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packInts
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.arturomarmolejo.yelpappcompose.R
import com.arturomarmolejo.yelpappcompose.core.UIState
import com.arturomarmolejo.yelpappcompose.data.model.Businesse
import com.arturomarmolejo.yelpappcompose.presentation.navigation.VerticalNavigationBar
import com.arturomarmolejo.yelpappcompose.presentation.navigation.VerticalNavigationDrawer
import com.arturomarmolejo.yelpappcompose.presentation.navigation.VerticalNavigationRail
import com.arturomarmolejo.yelpappcompose.presentation.viewmodel.YelpViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await


private const val SIGNIFICANT_DISTANCE_THRESHOLD = 100 // in meters
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HomeScreen(
    context: Context,
    navController: NavHostController,
    yelpViewModel: YelpViewModel,
    modifier: Modifier = Modifier
) {

    val windowSizeClass = calculateWindowSizeClass(context as Activity)

    Box(modifier = Modifier.fillMaxSize()) {
        val state = yelpViewModel.allBusinessesByLocation.collectAsStateWithLifecycle().value
        when(state) {
            is UIState.LOADING -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is UIState.SUCCESS -> {
                Scaffold(
                    bottomBar = {
                        when(windowSizeClass.widthSizeClass) {
                            WindowWidthSizeClass.Compact -> {
                                VerticalNavigationBar(navController)
                            }
                            WindowWidthSizeClass.Medium -> {
                                VerticalNavigationRail(navController)
                            }
                            WindowWidthSizeClass.Expanded -> {
                                VerticalNavigationDrawer(navController)
                            }
                        }
                    },
                ) { padding ->
                    BusinessesList(
                        businesses = state.response.businesses,
                        navController = navController
                    )
                }
            }
            is UIState.ERROR -> {
                Toast.makeText(LocalContext.current, "Error in response", Toast.LENGTH_LONG).show()
            }
        }

    }
}

@Composable
fun BusinessesList(
    businesses: List<Businesse>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    LazyColumn {
        itemsIndexed(businesses) { index, item ->
            BusinessItem(
                businesse = item,
                onItemClick = {
                    navController.navigate("DetailsScreen/${it.id}")
                }
            )
        }
    }
}

@Composable
fun BusinessItem(
    businesse: Businesse,
    onItemClick: (Businesse) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {
            onItemClick(businesse)
        },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
               AsyncImage(
                   modifier = modifier.size(100.dp),
                   model = businesse.imageUrl,
                   contentDescription = null,
                   placeholder = painterResource(R.drawable.baseline_place_24),
                   error = painterResource(R.drawable.baseline_sync_disabled_24)
               )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    text = businesse.name,
                )
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                    text = businesse.rating.toString(),
                )
            }
        }
    }
}