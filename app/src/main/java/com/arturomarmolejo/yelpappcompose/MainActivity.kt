package com.arturomarmolejo.yelpappcompose

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.arturomarmolejo.yelpappcompose.presentation.navigation.Navigation
import com.arturomarmolejo.yelpappcompose.presentation.viewmodel.YelpViewModel
import com.arturomarmolejo.yelpappcompose.ui.theme.YelpAppComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YelpAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    val navController: NavHostController = rememberNavController()
                    val yelpViewModel: YelpViewModel = hiltViewModel()


                    var location by remember {
                        mutableStateOf<Location?>(null)
                    }
                    var shouldRequestLocation by remember {
                        mutableStateOf(true)
                    }

                    val permissionState = rememberPermissionState(
                        permission = Manifest.permission.ACCESS_COARSE_LOCATION
                    )

                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                    val locationRequest = LocationRequest.Builder(10000L).build()

                    //Check location settings
                    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


                    LaunchedEffect(key1 = permissionState) {
                        if (!permissionState.status.isGranted) {
                            permissionState.launchPermissionRequest()
                        }
                    }

                    if (permissionState.status.isGranted) {
                        if (isLocationEnabled) {
                            //Request location updates
                            val locationCallback = object  : LocationCallback() {
                                override fun onLocationResult(result: LocationResult) {
                                    location = result.lastLocation
                                    shouldRequestLocation = false //Stop requesting location, lest it keeps updating the location value
                                }
                            }

                            LaunchedEffect(key1 = shouldRequestLocation) { //Only launch when should request location is true
                                if (shouldRequestLocation) {
                                    try {
                                        fusedLocationClient.requestLocationUpdates(
                                            locationRequest,
                                            locationCallback,
                                            Looper.getMainLooper()
                                        )
                                    } catch (e: Exception) {
                                        Toast.makeText(context,"Location not found", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }

                            //Use the location value
                            location?.let { currentLocation ->
                                yelpViewModel.getAllBusinessesByLocation(currentLocation.latitude, currentLocation.longitude)
                            }
                        } else {
                            Log.d("HomeScreen", "HomeScreen: No location enabled")
                        }
                    }

                    Navigation(context, location, yelpViewModel, navController)
                }
            }
        }
    }
}

