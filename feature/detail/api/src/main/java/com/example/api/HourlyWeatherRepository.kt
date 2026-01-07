package com.example.api

interface HourlyWeatherRepository {
    suspend fun getHourlyForecast(city: String): Result<List<HourlyForecast>>
}