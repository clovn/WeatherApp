package com.example.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("DailyForecasts")
    val list: List<ForecastResponseItem>
)

data class ForecastResponseItem(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Temperature")
    val temperature: Temp,
    @SerializedName("Day")
    val day: Icon,
)

data class Icon(
    @SerializedName("Icon")
    val icon: Int
)

data class Temp(
    @SerializedName("Minimum")
    val minimum: TempValue,
    @SerializedName("Maximum")
    val maximum: TempValue
)

data class TempValue(
    @SerializedName("Value")
    val value: Double
)