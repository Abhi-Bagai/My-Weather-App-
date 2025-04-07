package com.bcit.myweatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bcit.myweatherapp.data.Location
import com.bcit.myweatherapp.data.Repository

class LocationState (private val repository: Repository) : ViewModel(){
    var location by mutableStateOf<Location?>(null)

    suspend fun getLocation(){
        location = repository.getLocation()
    }

}