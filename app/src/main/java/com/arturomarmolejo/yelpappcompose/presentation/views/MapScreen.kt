package com.arturomarmolejo.yelpappcompose.presentation.views

import android.graphics.Camera
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturomarmolejo.yelpappcompose.core.UIState
import com.arturomarmolejo.yelpappcompose.presentation.viewmodel.YelpViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    yelpViewModel: YelpViewModel,
    location: Location?,
) {

    val businessState = yelpViewModel.allBusinessesByLocation.collectAsStateWithLifecycle().value
    val cameraPositionState = rememberCameraPositionState {
        location?.let {
            CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 10f)
        }
    }

    LaunchedEffect(location) {
        location?.let {
           cameraPositionState.position = CameraPosition.fromLatLngZoom(
               LatLng(it.latitude, it.longitude), 10f
           )
        }
    }

    when(businessState) {
        is UIState.LOADING -> {
            CircularProgressIndicator()
        }
        is UIState.SUCCESS -> {
            val businessList = businessState.response.businesses
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                businessList.forEach { business ->
                    val latLng = LatLng(business.coordinates.latitude, business.coordinates.longitude)
                    Marker(
                        state = MarkerState(position = latLng),
                        title = business.name,
                        snippet = business.location.address1
                    )
                }
            }
        }
        is UIState.ERROR -> {
            Toast.makeText(LocalContext.current, "Error in response", Toast.LENGTH_LONG).show()
        }
    }
}