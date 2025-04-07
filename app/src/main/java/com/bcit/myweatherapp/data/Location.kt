package com.bcit.myweatherapp.data

import com.google.gson.annotations.SerializedName

data class Location(
    val city: String?,
    @SerializedName("latitude")
    val lat: Float?,
    @SerializedName("longitude")
    val long: Float?
)