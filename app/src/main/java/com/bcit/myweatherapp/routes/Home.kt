package com.bcit.myweatherapp.routes

import androidx.compose.ui.text.font.FontFamily


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.bcit.myweatherapp.CityWeatherState
import com.bcit.myweatherapp.LocationState
import com.bcit.myweatherapp.R
import com.bcit.myweatherapp.data.CityWeather
import com.bcit.myweatherapp.data.IMAGE
import com.bcit.myweatherapp.data.Repository
import com.bcit.myweatherapp.data.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class CityCard(val city: String, val color: Long)

@Composable
fun Home(
    navController: NavController,
    cityWeather: WeatherResponse,
    cityWeatherState: CityWeatherState,
    locationState: LocationState,
    repository: Repository
) {
    val cities = stringArrayResource(R.array.cities)
    val popularCityList = mutableListOf<CityCard>()
    val colorsList = listOf(
        0xFF03045e,
        0xFF023e8a,
        0xFF0077b6,
        0xFF0096c7,
        0xFF00b4d8,
        0xFF48cae4,
        0xFF90e0ef,
        0xFFade8f4,
        0xFFcaf0f8
    )

    for (i in cities.indices) {
        popularCityList.add(CityCard(cities[i], colorsList[i]))

    }
    var stateListCity = remember {
        popularCityList.toMutableStateList()
    }
    val merriweatherFont = FontFamily(
        Font(R.font.merriweather_regular, FontWeight.Normal),

        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        item {
            Search(cityWeatherState)
            Text(cityWeather.toString())
            MyLocation(locationState, cityWeatherState, repository)
            Text("${locationState.location}")
        }


        item {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End

            ) {
                Text(

                    text = "Popular Cities",
                    style = TextStyle(
                        fontFamily = merriweatherFont,
                        fontSize = 25.sp,
                        color = Color(0xFF020356)

                    )
                )
            }
        }
        items(stateListCity.size) {
            MyCard(
                navController,
                cityWeather,
                stateListCity[it],
                merriweatherFont,
                cityWeatherState,
                repository
            )
        }
    }
}

@Composable
fun MyCard(
    navController: NavController,
    cityWeather: WeatherResponse,
    cityCard: CityCard,
    font: FontFamily,
    cityWeatherState: CityWeatherState,
    repository: Repository

) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .clickable {
                scope.launch {
                    cityWeatherState.cityWeather = repository.search(cityCard.city)
                    navController.navigate("detail")
                }


            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(cityCard.color))
                .padding(20.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
//            AsyncImage(
//                model = IMAGE.format(cityWeather?.weather?.get(0)?.icon),
//                contentDescription = null,
//                modifier = Modifier
//                    .safeDrawingPadding()
//                    .size(50.dp)
//
//            )


            Text(
                text = "${cityCard.city}",
                style = TextStyle(
                    fontFamily = font,
                    fontSize = 30.sp,
                    color = Color(0xFFD03B12)

                )
            )

        }

    }
}

@Composable
fun Search(cityWeatherState: CityWeatherState) {
    TextField(
        cityWeatherState.searchFlow.collectAsState().value,
        onValueChange = {
            cityWeatherState.searchFlow.value = it

        })
}


@Composable
fun MyLocation(
    locationState: LocationState,
    cityWeatherState: CityWeatherState,
    repository: Repository
) {
    val scope = rememberCoroutineScope()
    val location = locationState.location

    Button(onClick = {
        scope.launch {
            locationState.getLocation()
            delay(1000L)
            val myCity = locationState.location?.city
            cityWeatherState.cityWeather = myCity?.let { repository.search(it) }
        }
    }) {
        Text("My Location")
    }

}