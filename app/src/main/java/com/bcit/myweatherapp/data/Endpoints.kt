package com.bcit.myweatherapp.data

///api endpoints
const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather?units=metric&"
const val API_KEY = "7213ecad8ac10d7de17cee4ec28f5075"
const val SEARCH_CITY = "${BASE_URL}q=%S&appid=${API_KEY}"
const val IMAGE = "https://openweathermap.org/img/wn/%s@2x.png"
const val IP_GEO_API_KEY = "5137952474c5461d872d1e4a997d45af"
const val LOCATION_URL = "https://api.ipgeolocation.io/ipgeo?apiKey=${IP_GEO_API_KEY}"


const val googleapu = "AIzaSyBAXlG8cvJn1SqKBvxC7HP0VSOtWB7QCi0"

// https://api.openweathermap.org/data/2.5/weather?q=New%20Westminster&appid=7213ecad8ac10d7de17cee4ec28f5075
