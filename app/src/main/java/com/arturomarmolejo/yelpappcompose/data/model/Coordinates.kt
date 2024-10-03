package com.arturomarmolejo.yelpappcompose.data.model


import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("longitude")
    val longitude: Double = 0.0
)