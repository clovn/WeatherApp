package com.example.impl.domain

import com.example.api.HourlyWeatherRepository
import com.example.local.SharedPrefsManager

class HourlyForecastUseCase(
    private val repository: HourlyWeatherRepository,
    private val prefs: SharedPrefsManager
) {
    suspend fun invoke() = repository.getHourlyForecast(prefs.getSelectedCity() ?: "Москва")
}