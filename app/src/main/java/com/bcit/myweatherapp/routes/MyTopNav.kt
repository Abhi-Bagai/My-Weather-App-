package com.bcit.myweatherapp.routes


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bcit.myweatherapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopNav(
    navController: NavController
){
    val merriweatherFont = FontFamily(
        Font(R.font.merriweather_regular, FontWeight.Normal),

        )
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "MyWeatherApp",
                style = TextStyle(
                    fontFamily = merriweatherFont,
                    fontSize = 30.sp,
                    color = Color(0xFF020356)

                )
            )

        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }

        },

        actions = {
            IconButton(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)

                )
            }
        }

    )

}