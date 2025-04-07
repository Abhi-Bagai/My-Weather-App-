package com.bcit.myweatherapp.data

// Data class representing the entire weather response
data class WeatherResponse(
    val coord: Coord = Coord(),
    val weather: List<Weather> = listOf(Weather()),
    val base: String = "",
    val main: Main = Main(),
    val visibility: Int = 0,
    val wind: Wind = Wind(),
    val clouds: Clouds = Clouds(),
    val dt: Long = 0L,
    val sys: Sys = Sys(),
    val id: Int = 0,
    val name: String = "",
    val cod: Int = 0
)

// Data class for coordinates
data class Coord(
    val lon: Double = 0.0,
    val lat: Double = 0.0
)

// Data class for weather information
data class Weather(
    val id: Int = 0,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
)

// Data class for main weather metrics
data class Main(
    val temp: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val temp_min: Double = 0.0,
    val temp_max: Double = 0.0
)

// Data class for wind information
data class Wind(
    val speed: Double = 0.0,
    val deg: Int = 0
)

// Data class for cloud information
data class Clouds(
    val all: Int = 0
)

// Data class for system information
data class Sys(
    val type: Int = 0,
    val id: Int = 0,
    val message: Double = 0.0,
    val country: String = "",
    val sunrise: Long = 0L,
    val sunset: Long = 0L
)
