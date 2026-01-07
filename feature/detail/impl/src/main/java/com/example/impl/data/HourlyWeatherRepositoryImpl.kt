package com.example.impl.data

import android.util.Log
import com.example.api.HourlyForecast
import com.example.api.HourlyWeatherRepository
import com.example.network.api.WeatherApiService
import com.example.network.safeApiCall
import com.example.util.hourlyForecastFormat
import java.time.LocalDateTime

class HourlyWeatherRepositoryImpl(
    private val weatherApi: WeatherApiService
): HourlyWeatherRepository {
    override suspend fun getHourlyForecast(city: String): Result<List<HourlyForecast>> = safeApiCall {
        val location = weatherApi.getLocationKey(city)[0]
        Log.d("DEBUG", location.toString())
        Log.d("DEBUG", weatherApi.getHourlyForecast(location.key).toString())
        weatherApi.getHourlyForecast(location.key).map { forecast ->
            HourlyForecast(
                time = hourlyForecastFormat(LocalDateTime.parse(forecast.date.take(19))),
                temperature = forecast.temperature.value.toInt(),
                feelsLike = forecast.temperature.value.toInt(),
                windSpeed = forecast.windSpeed.speed.value.toInt(),
                humidity = forecast.humidity,
            )
        }
    }
}