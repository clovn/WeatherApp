package com.example.api

interface WeatherRepository {
    suspend fun getWeather(city: String): Result<WeatherData>
}