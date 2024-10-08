package com.arturomarmolejo.yelpappcompose.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.arturomarmolejo.yelpappcompose.core.UIState
import com.arturomarmolejo.yelpappcompose.core.UIState.*
import com.arturomarmolejo.yelpappcompose.data.model.Businesse
import com.arturomarmolejo.yelpappcompose.presentation.navigation.Routes
import com.arturomarmolejo.yelpappcompose.presentation.viewmodel.YelpViewModel

@Composable
fun DetailsScreen(
    navBackStackEntry: NavBackStackEntry,
    yelpViewModel: YelpViewModel,
    modifier: Modifier = Modifier
) {
    val itemId = navBackStackEntry.arguments?.getString("itemId")

    //Trigger data fetching when itemId is available
    LaunchedEffect(itemId) {
        itemId?.let {
            yelpViewModel.getBusinessById(it)
        }
    }

    val businessState = yelpViewModel.singleBusiness.collectAsStateWithLifecycle().value
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when(businessState) {
            is LOADING -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is UIState.SUCCESS -> {

                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    text = businessState.response?.name ?: ""
                )

            }
            is UIState.ERROR -> {
                Toast.makeText(LocalContext.current, "Error in response", Toast.LENGTH_LONG).show()
            }
        }
    }
}
