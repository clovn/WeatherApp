package com.example.api

data class HourlyForecast(
    val time: String,
    val temperature: Int,
    val feelsLike: Int,
    val windSpeed: Int,
    val humidity: Int,
)