package com.bcit.myweatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import com.bcit.myweatherapp.data.FavCityRepository
import com.bcit.myweatherapp.data.FavoriteCity

class FavCityState (private val favCityRepo: FavCityRepository) {

    var cities = favCityRepo.getAll().toMutableStateList()

    suspend fun add(city: FavoriteCity){
        favCityRepo.insertEntity(city)
    }

    fun refresh(){
        cities.apply {
            clear()
            addAll(favCityRepo.getAll())
        }

    }
    fun remove(city: FavoriteCity){
        favCityRepo.remove(city)
    }



}