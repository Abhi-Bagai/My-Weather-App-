package com.bcit.myweatherapp.data

///api endpoints
const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"
const val API_KEY = "7213ecad8ac10d7de17cee4ec28f5075"
const val SEARCH_CITY = "${BASE_URL}?q=%S&appid=${API_KEY}"
const val IMAGE = "https://openweathermap.org/img/wn/%s@2x.png"