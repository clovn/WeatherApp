package com.example.api

data class WeatherData(
    val city: String,
    val date: String,
    val weatherIconUrl: String,
    val weatherText: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val feelsLike: Double,
    val forecast: List<ForecastDay>,
    val photoURL: String
)

data class ForecastDay(
    val date: String,
    val iconUrl: Int,
    val tempDay: Int,
    val tempNight: Int
)