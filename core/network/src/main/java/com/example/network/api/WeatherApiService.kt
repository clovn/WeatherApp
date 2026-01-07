package com.example.network.api

import com.example.network.dto.ForecastResponse
import com.example.network.dto.LocationResponse
import com.example.network.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {
    @GET("currentconditions/v1/{locationKey}?details=true&getPhotos=true")
    suspend fun getWeather(@Path("locationKey") key: String): List<WeatherResponse>

    @GET("forecasts/v1/daily/5day/{locationKey}?metric=true")
    suspend fun getForecast(@Path("locationKey") key: String): ForecastResponse

    @GET("locations/v1/cities/search")
    suspend fun getLocationKey(@Query("q") city: String): List<LocationResponse>
}