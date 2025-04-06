package com.bcit.myweatherapp.data


import coil3.util.CoilUtils.result
import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class CityWeather(

    val name: String?,
    val weather: JsonArray,
    val main: JsonObject?,
    val visibility: Int?,
    val wind: JsonObject?,
    val clouds: JsonObject?,
    val sys: JsonObject?,

    


)




