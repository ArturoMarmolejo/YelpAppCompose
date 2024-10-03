package com.arturomarmolejo.yelpappcompose.data.model


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address1")
    val address1: String = "",
    @SerializedName("address2")
    val address2: String? = null,
    @SerializedName("address3")
    val address3: String? = null,
    @SerializedName("city")
    val city: String = "",
    @SerializedName("country")
    val country: String = "",
    @SerializedName("display_address")
    val displayAddress: List<String> = listOf(),
    @SerializedName("state")
    val state: String = "",
    @SerializedName("zip_code")
    val zipCode: String = ""
)