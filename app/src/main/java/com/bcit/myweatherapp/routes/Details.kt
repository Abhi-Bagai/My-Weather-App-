package com.bcit.myweatherapp.routes

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bcit.myweatherapp.data.WeatherResponse
import kotlin.math.roundToInt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.bcit.myweatherapp.R
import com.bcit.myweatherapp.data.IMAGE
import java.util.*

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun Detail(cityInfo: WeatherResponse) {
    Box(modifier = Modifier.safeDrawingPadding()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // City name and country
            Text(
                text = "${cityInfo.name}, ${cityInfo.sys.country}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Weather icon and description
            val weatherItem = cityInfo.weather.firstOrNull()
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

            Spacer(modifier = Modifier.height(24.dp))

            // Temperature and main weather data
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF99D0DC))

            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${cityInfo.main.temp.roundToInt()}°C",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Min",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${cityInfo.main.temp_min.roundToInt()}°C",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Max",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${cityInfo.main.temp_max.roundToInt()}°C",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }


                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weather details grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    WeatherDetailCard(
                        title = "Humidity",
                        value = "${cityInfo.main.humidity}%",
                        icon = R.drawable.humidity,
                    )
                }

                item {
                    WeatherDetailCard(
                        title = "Pressure",
                        value = "${cityInfo.main.pressure} hPa",
                        icon = R.drawable.pressure
                    )
                }

                item {
                    WeatherDetailCard(
                        title = "Wind",
                        value = "${cityInfo.wind.speed} m/s",
                        icon = R.drawable.wind
                    )
                }

                item {
                    WeatherDetailCard(
                        title = "Visibility",
                        value = "${cityInfo.visibility / 1000} km",
                        icon = R.drawable.visibility
                    )
                }

                item {
                    val timestamp = cityInfo.sys.sunrise * 1000L
                    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
                    WeatherDetailCard(
                        title = "Sunrise",
                        value = time,
                        icon = R.drawable.sunrise

                    )
                }

                item {
                    val timestamp = cityInfo.sys.sunset * 1000L
                    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
                    WeatherDetailCard(
                        title = "Sunset",
                        value = time,
                        icon = R.drawable.sunset
                    )
                }
            }
        }
    }
}
@Composable
fun WeatherDetailCard(
    title: String,
    value: String,
    icon: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF99D0DC)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
