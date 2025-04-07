package com.bcit.myweatherapp.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class Repository(private val httpClient: HttpClient){

    suspend fun search(city:String): WeatherResponse?{
        //call get from client
        val response = httpClient.get(SEARCH_CITY.format(city))
        //get response as a json string
        val json = response.body<JsonObject>().toString()
        println(json)
        return Gson().fromJson(json, WeatherResponse::class.java )
    }

    suspend fun getLocation() : Location?{
        val response = httpClient.get(LOCATION_URL)
        val json = response.body<JsonObject>().toString()
        println("my location: " + json)
        return Gson().fromJson(json, Location::class.java)
    }






}