package com.bcit.myweatherapp;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcit.myweatherapp.data.CityWeather
import com.bcit.myweatherapp.data.Repository;
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class CityWeatherState(private val repository: Repository) : ViewModel(){

    var weather by mutableStateOf<CityWeather?>(null)
    var searchFlow = MutableStateFlow("")

    init {
        getCityInput()
    }

    private fun getCityInput(){
        viewModelScope.launch {
            //collection point
            searchFlow
                .debounce(5000L)
                .collect{
                    weather = repository.search(it)
                }
        }


    }




}
