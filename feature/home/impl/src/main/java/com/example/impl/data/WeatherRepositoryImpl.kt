package com.example.impl.data

import com.example.api.ForecastDay
import com.example.api.WeatherData
import com.example.api.WeatherRepository
import com.example.network.api.WeatherApiService
import com.example.network.safeApiCall
import com.example.util.forecastFormat
import com.example.util.homeScreenFormat
import java.time.LocalDateTime

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(city: String): Result<WeatherData> = safeApiCall {
        val location = weatherApi.getLocationKey(city)[0]
        val weather = weatherApi.getWeather(location.key)[0]
        val forecast = weatherApi.getForecast(location.key)
        WeatherData(
            city = location.localizedName,
            date = homeScreenFormat(LocalDateTime.parse(weather.localObservationDateTime.take(19))),
            weatherIconUrl = weather.weatherIcon,
            weatherText = weather.weatherText,
            temperature = weather.temperature.metric.value,
            humidity = weather.temperature.metric.value,
            windSpeed = weather.wind.speed.value,
            feelsLike = weather.realFeelTemperature.metric.value,
            forecast = forecast.list.map { forecastResponse ->
                ForecastDay(
                    date = forecastFormat(LocalDateTime.parse(forecastResponse.date.take(19))),
                    iconUrl = forecastResponse.day.icon,
                    tempDay = forecastResponse.temperature.maximum.value.toInt(),
                    tempNight = forecastResponse.temperature.minimum.value.toInt()
                )
            },
            photoURL = weather.photos.random().portraitLink.replaceFirst("http", "https")
        )
    }
}