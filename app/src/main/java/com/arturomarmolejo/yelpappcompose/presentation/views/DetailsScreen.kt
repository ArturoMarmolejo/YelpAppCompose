package com.arturomarmolejo.yelpappcompose.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arturomarmolejo.yelpappcompose.data.model.Businesse
import com.arturomarmolejo.yelpappcompose.presentation.navigation.Routes

@Composable
fun DetailsScreen(
    item: Businesse? = null
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Text( text = item?.name ?: "")
        }
    }
}