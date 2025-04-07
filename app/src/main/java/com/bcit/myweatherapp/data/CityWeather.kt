package com.bcit.myweatherapp.data


import coil3.util.CoilUtils.result
import com.google.gson.JsonArray
import com.google.gson.JsonObject
//
data class CityWeather(

    val name: String?,
    val weather: JsonArray,
    val main: JsonObject?,
    val visibility: Int?,
    val wind: JsonObject?,
    val clouds: JsonObject?,
    val sys: JsonObject?,

)
// Data class representing the entire weather response
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val id: Int,
    val name: String,
    val cod: Int
)

// Data class for coordinates
data class Coord(
    val lon: Double,
    val lat: Double
)

// Data class for weather information
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

// Data class for main weather metrics
data class Main(
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val temp_min: Double,
    val temp_max: Double
)

// Data class for wind information
data class Wind(
    val speed: Double,
    val deg: Int
)

// Data class for cloud information
data class Clouds(
    val all: Int
)

// Data class for system information
data class Sys(
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)



