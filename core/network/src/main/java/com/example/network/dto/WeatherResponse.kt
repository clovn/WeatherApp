package com.example.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,
    @SerializedName("WeatherText")
    val weatherText: String,
    @SerializedName("WeatherIcon")
    val weatherIcon: String,
    @SerializedName("Temperature")
    val temperature: Temperature,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature: Temperature,
    @SerializedName("RelativeHumidity")
    val relativeHumidity: Int,
    @SerializedName("Wind")
    val wind: WindSpeed,
    @SerializedName("Photos")
    val photos: List<Photo>
)

data class Photo(
    @SerializedName("PortraitLink")
    val portraitLink: String
)

data class Temperature(
    @SerializedName("Metric")
    val metric: Metric
)


data class WindSpeed(
    @SerializedName("Speed")
    val speed: Metric
)

data class Metric(
    @SerializedName("Value")
    val value: Double
)