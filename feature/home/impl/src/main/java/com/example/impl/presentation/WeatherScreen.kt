package com.example.impl.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.api.ForecastDay
import org.koin.compose.koinInject
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinInject(),
    navigateToLogin: () -> Unit,
    navigateToPick: () -> Unit
) {
    val state by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(WeatherEvent.LoadWeather)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            is WeatherState.Logout -> {
                navigateToLogin()
            }
            is WeatherState.Loading -> {
                CircularProgressIndicator()
            }

            is WeatherState.Error -> {
                Text(text = "Error: ${(state as WeatherState.Error).error}", color = Color.Red)
            }

            is WeatherState.Success -> {
                (state as WeatherState.Success).data?.let { weatherData ->
                    AsyncImage(
                        model = weatherData.photoURL,
                        contentDescription = "Background",
                        modifier = Modifier
                            .matchParentSize(),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                            .padding(top = 32.dp, start = 16.dp, bottom = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    navigateToPick()
                                }
                            ) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color.White
                                )
                                Text(" ${weatherData.city}", color = Color.White, fontSize = 18.sp)
                            }
                            Icon(Icons.Default.Logout, contentDescription = "Menu", tint = Color.White, modifier = Modifier.clickable {
                                viewModel.onEvent(WeatherEvent.Logout)
                            })
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 32.dp).fillMaxWidth()
                        ) {
                            Text(
                                text = weatherData.date,
                                color = Color.White,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 24.dp).fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = "https://www.accuweather.com/assets/images/weather-icons/v2a/1.svg",
                                contentDescription = "Weather Icon",
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text(
                                text = weatherData.weatherText,
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "${weatherData.temperature}°C",
                                color = Color.White,
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp, horizontal = 16.dp)
                        ) {
                            StatItem("Humidity", "${weatherData.humidity}%", Icons.Default.WaterDrop)
                            StatItem("Wind", "${weatherData.windSpeed} km/h", Icons.Default.Air)
                            StatItem("Feels Like", "${weatherData.feelsLike}°", Icons.Default.Thermostat)
                        }


                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .fillMaxWidth()
                                .wrapContentSize()
                                .background(
                                    color = Color.Black.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                            ) {
                                weatherData.forecast.forEach { day ->
                                    ForecastItem(day)
                                }
                            }
                        }


                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Icon(icon, contentDescription = title, tint = Color.White)
        Text(title, color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
        Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ForecastItem(day: ForecastDay) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize().padding(horizontal = 8.dp)
    ) {
        Text(day.date, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        AsyncImage(
            model = day.iconUrl,
            contentDescription = "Forecast Icon",
            modifier = Modifier.size(32.dp),
            contentScale = ContentScale.Fit
        )
        Text("${day.tempDay}°", color = Color.White, fontSize = 14.sp)
        Text("${day.tempNight}°", color = Color.Gray, fontSize = 12.sp)
    }
}