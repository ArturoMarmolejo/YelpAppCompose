package com.arturomarmolejo.yelpappcompose.data.model


import com.google.gson.annotations.SerializedName

data class YelpResponse(
    @SerializedName("businesses")
    val businesses: List<Businesse> = listOf(),
    @SerializedName("region")
    val region: Region = Region(),
    @SerializedName("total")
    val total: Int = 0
)