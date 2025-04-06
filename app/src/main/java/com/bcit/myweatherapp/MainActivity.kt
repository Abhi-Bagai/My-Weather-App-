package com.bcit.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.bcit.myweatherapp.data.IMAGE
import com.bcit.myweatherapp.data.Repository
import com.bcit.myweatherapp.data.client
import com.bcit.myweatherapp.ui.theme.MyWeatherAppTheme
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    private val repository by lazy{
        Repository(client)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val cityWeatherState = viewModel{
                CityWeatherState(repository)
            }
            val cityWeather = cityWeatherState.weather
            Box(modifier = Modifier.safeDrawingPadding()){
                LazyColumn {
                    item {
                        Search()
                    }
                    items(1){
                        Text( cityWeather.toString() )

                        val iconCode = cityWeather?.weather?.get(0)?.asJsonObject?.get("icon")?.asString ?: "unknown"
                        Text("Icon code: $iconCode")

                        AsyncImage(
                            model = IMAGE.format(iconCode),
                            contentDescription = null,
                            modifier = Modifier
                                .safeDrawingPadding()
                                .size(100.dp)

                        )

                    }
                }
            }
        }
    }

    @Composable
    fun Search() {
        val cityWeatherState:CityWeatherState = viewModel()

        TextField(
            cityWeatherState.searchFlow.collectAsState().value,
            onValueChange = {
                cityWeatherState.searchFlow.value = it

            })


    }



}

