package com.example.network.dto

import com.google.gson.annotations.SerializedName

data class HourlyForecastResponse (
    @SerializedName("DateTime")
    val date: String,
    @SerializedName("Temperature")
    val temperature: TempValue,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature: TempValue,
    @SerializedName("Wind")
    val windSpeed: Wind,
    @SerializedName("RelativeHumidity")
    val humidity: Int
)

data class Wind(
    @SerializedName("Speed")
    val speed: TempValue
)