package com.arturomarmolejo.yelpappcompose.presentation.views

import android.Manifest
import android.provider.Contacts.Intents.UI
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.arturomarmolejo.yelpappcompose.presentation.viewmodel.YelpViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    yelpViewModel: YelpViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    LaunchedEffect(key1 = permissionState) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    if (permissionState.status.isGranted) {
        LaunchedEffect(key1 = Unit) {
            try {
                val location = fusedLocationClient.lastLocation.await()
                location?.let {
                    yelpViewModel.getAllBusinessesByLocation(it.latitude, it.longitude)
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Current location not found", Toast.LENGTH_LONG).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val state = yelpViewModel.allBusinessesByLocation.collectAsStateWithLifecycle().value
        when(state) {
            is UIState.LOADING -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is UIState.SUCCESS -> {
                BusinessesList(
                    businesses = state.response.businesses,
                    navController = navController
                )
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
                    navController.navigate("DetailsScreen/$it")
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
                    style = MaterialTheme.typography.labelLarge,
                    text = businesse.name,
                )
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    text = businesse.name,
                )
            }
        }
    }
}