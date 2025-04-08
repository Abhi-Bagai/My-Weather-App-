package com.bcit.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.bcit.myweatherapp.data.FavCityRepository
import com.bcit.myweatherapp.data.IMAGE
import com.bcit.myweatherapp.data.MyDatabase
import com.bcit.myweatherapp.data.Repository
import com.bcit.myweatherapp.data.WeatherResponse
import com.bcit.myweatherapp.data.client
import com.bcit.myweatherapp.routes.Detail
import com.bcit.myweatherapp.routes.Favorite
import com.bcit.myweatherapp.routes.Home
import com.bcit.myweatherapp.routes.MyTopNav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {

    private val db by lazy {
        MyDatabase.getDatabase(applicationContext)
    }

    private val favCityRepo by lazy{
        FavCityRepository(db.favDao())
    }

    private val repository by lazy {
        Repository(client)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var cityWeatherState = viewModel { CityWeatherState(repository) }
            var locationState = remember { LocationState(repository) }
            var cityWeather = cityWeatherState.cityWeather
            val favCityState = FavCityState(favCityRepo)


            Box(
                modifier = Modifier
                    .safeDrawingPadding()
                    .fillMaxSize()
            ) {

                if (cityWeather != null) {
                    MainContent(
                        cityWeather = cityWeather,
                        locationState = locationState,
                        cityWeatherState = cityWeatherState,
                        favCityState = favCityState
                    )
                } else {
                    Text( "Abhi's Weather App is Loading...")

                }
            }


        }
    }
    

    @Composable
    fun MainContent(
        cityWeather: WeatherResponse,
        locationState: LocationState,
        cityWeatherState: CityWeatherState,
        favCityState : FavCityState,


    ) {
        val navController = rememberNavController()

        Scaffold(topBar = {
            Column {
                MyTopNav(navController, favCityState)
            }

        }) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)

            ) {

                // destination Home
                composable("home") {
                    Home(
                        navController, cityWeather, cityWeatherState, locationState, repository,
                        favCityState = favCityState
                    )

                }

                // destination details
                composable("detail") {
                    Detail(
                        cityWeather,
                        favCityState = favCityState
                    )

                }
                // destination favs
                composable("favorite") {
                     Favorite(
                         navController = navController,
                         favCityState = favCityState,
                         cityWeatherState
                     )

                }

            }
        }


    }

}



