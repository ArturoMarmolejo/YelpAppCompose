package com.arturomarmolejo.yelpappcompose.data.model


import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("center")
    val center: Center = Center()
)