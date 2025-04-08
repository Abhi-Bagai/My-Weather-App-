package com.bcit.myweatherapp.routes

import androidx.compose.ui.text.font.FontFamily


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.bcit.myweatherapp.FavCityState
import com.bcit.myweatherapp.LocationState
import com.bcit.myweatherapp.R
import com.bcit.myweatherapp.data.FavCityRepository
import com.bcit.myweatherapp.data.FavoriteCity
import com.bcit.myweatherapp.data.IMAGE
import com.bcit.myweatherapp.data.Repository
import com.bcit.myweatherapp.data.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class CityCard(val city: String, val color: Long)

@Composable
fun Home(
    navController: NavController,
    cityWeather: WeatherResponse,
    cityWeatherState: CityWeatherState,
    locationState: LocationState,
    repository: Repository,

    favCitiesRepo: FavCityRepository
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Search(
                    cityWeatherState,
                    merriweatherFont,
                )
                Spacer(modifier = Modifier.size(10.dp))
                MyLocation(
                    locationState,
                    cityWeatherState,
                    repository,
                    merriweatherFont,
                    navController
                )
            }
        }
        item {
            CityPreview(navController, cityWeather)
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

            for (city in stateListCity){

            }

            MyCard(
                navController,
                stateListCity[it],
                merriweatherFont,
                cityWeatherState,
                repository,
                favCitiesRepo



            )
        }
    }
}

@Composable
fun MyCard(
    navController: NavController,
    cityCard: CityCard,
    font: FontFamily,
    cityWeatherState: CityWeatherState,
    repository: Repository,
    favCitiesRepo: FavCityRepository

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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "${cityCard.city}",
                style = TextStyle(
                    fontFamily = font,
                    fontSize = 30.sp,
                    color = Color(0xFFD03B12)
                )
            )

            // favourites icon orange
            IconButton(
                onClick = {
                    scope.launch {
                        val city = FavoriteCity(cityName = cityCard.city)
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            favCityState.remove(city)
                        } else {
                            favCityState.add(city)
                        }
                        favCityState.refresh()
                    }
                }
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp),
                    tint = if (isFavorite) Color(0xFFD03B12) else Color.Gray
                )
            }


        }
    }
}

@Composable
fun Search(
    cityWeatherState: CityWeatherState,
    font: FontFamily,
) {
    TextField(
        cityWeatherState.searchFlow.collectAsState().value,
        onValueChange = { cityWeatherState.searchFlow.value = it },
        placeholder = {
            Text(
                "Search City",
                style = TextStyle(
                    fontFamily = font,
                    fontSize = 12.sp,
                    color = Color(0xFFF36F4D)
                )
            )
        },
        modifier = Modifier
            .height(56.dp)
            .width(250.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFEB5E28),
            unfocusedTextColor = Color(0xFFEB5E28),
            focusedContainerColor = Color(0xFFCCC5B9),  // light orange background
            unfocusedContainerColor = Color(0xFFCCC5B9),
            cursorColor = Color(0xFFEB5E28),  // orange cursor

        ),
        textStyle = TextStyle(
            fontFamily = font,
            fontSize = 25.sp,
            color = Color(0xFFEB5E28)
        )


    )

}


@Composable
fun MyLocation(
    locationState: LocationState,
    cityWeatherState: CityWeatherState,
    repository: Repository,
    font: FontFamily,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
//    val location = locationState.location

    Button(
        colors = ButtonColors(
            contentColor = Color(0xFF8C9EFF),
            containerColor = Color(0xFFCCC5B9),
            disabledContainerColor = Color(0xFF98CEDA),
            disabledContentColor = Color(0xFFFF80AB)
        ),
        onClick = {
            scope.launch {
                locationState.getLocation()
                delay(1000L)
                val myCity = locationState.location?.city
                cityWeatherState.cityWeather = myCity?.let { repository.search(it) }
                navController.navigate("detail")
            }

        },
        modifier = Modifier
            .height(56.dp)
            .width(120.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(Color(0xFFCCC5B9)),


        ) {
        Text(
            text = "Use My Location",
            style = TextStyle(
                fontFamily = font,
                fontSize = 12.sp,
                color = Color(0xFFEB5E28)

            )
        )

    }

}

@Composable
fun CityPreview(
    navController: NavController,
    cityWeather: WeatherResponse,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier.safeDrawingPadding(),
            onClick = {
                navController.navigate("detail")
            },
            colors = CardColors(
                contentColor = Color(0xFF474749),
                containerColor = Color(0xFFCCC5B9),
                disabledContainerColor = Color(0xFF98CEDA),
                disabledContentColor = Color(0xFFFF80AB)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // City name and country
                Text(
                    text = "${cityWeather.name} ${cityWeather.sys.country}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Weather icon and description
                val weatherItem = cityWeather.weather.firstOrNull()
                weatherItem?.let { weather ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = IMAGE.format(weather.icon),
                            contentDescription = weather.description,
                            modifier = Modifier.size(80.dp)
                        )

                        Column {
                            Text(
                                text = weather.main,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = weather.description.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

    }

}