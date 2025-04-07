package com.bcit.myweatherapp;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bcit.myweatherapp.data.Repository
import com.bcit.myweatherapp.data.WeatherResponse
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class CityWeatherState(
    private val repository: Repository) : ViewModel(){

    var cityWeather by mutableStateOf<WeatherResponse?>(null)
    var searchFlow = MutableStateFlow("")

    init {
        getWeather("London")
        getCityInput()
    }

    private fun getWeather(city: String) {
        viewModelScope.launch {
            cityWeather = repository.search(city)
        }
    }

    private fun getCityInput(){
        viewModelScope.launch {
            //collection point
            searchFlow
                .debounce(5000L)
                .collect{
                    cityWeather = repository.search(it)
                }
        }
        println("This lin" + cityWeather)

    }

}
//
//    private fun getMyWeather(){
//        val myCity = locationState.location?.city
//        println(myCity)
//        viewModelScope.launch {
//            cityWeather = myCity?.let { repository.search(it) }
//        }
//
//    }







