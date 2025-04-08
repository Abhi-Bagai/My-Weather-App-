package com.bcit.myweatherapp.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bcit.myweatherapp.CityWeatherState
import com.bcit.myweatherapp.FavCityState
import com.bcit.myweatherapp.R
import com.bcit.myweatherapp.data.FavoriteCity
import kotlinx.coroutines.launch

@Composable
fun Favorite(
    navController: NavController,
    favCityState: FavCityState,
    cityWeatherState: CityWeatherState

) {


        val merriweatherFont = FontFamily(
        Font(R.font.merriweather_regular, FontWeight.Normal),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Favourite Cities",
                    style = TextStyle(
                        fontFamily = merriweatherFont,
                        fontSize = 25.sp,
                        color = Color(0xFF020356)
                    )
                )
            }
        }
        items(favCityState.cities.size) {
            MyFavCard(
                navController,
                merriweatherFont,
                favCityState.cities[it],
                favCityState,
                cityWeatherState = cityWeatherState
            )
        }
    }
}




@Composable
fun MyFavCard(
    navController: NavController,
    font: FontFamily,
    favoriteCity: FavoriteCity,
    favCityState: FavCityState,
    cityWeatherState: CityWeatherState
    ) {

    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .clickable {
                scope.launch {
                    favoriteCity.cityName?.let { cityWeatherState.getWeather(it) }
                    println(favoriteCity.cityName)
                    navController.navigate("detail")
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffcdcdce))
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = favoriteCity.cityName ?: "unknown",
                style = TextStyle(
                    fontFamily = font,
                    fontSize = 30.sp,
                    color = Color(0xFFD03B12)

                )
            )
            // favourites icon orange
            IconButton(
                onClick = {
                    favCityState.remove(favoriteCity)
                    favCityState.refresh()
                }
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp),
                    tint = Color(0xFFD03B12)



                )
            }


        }

    }
}