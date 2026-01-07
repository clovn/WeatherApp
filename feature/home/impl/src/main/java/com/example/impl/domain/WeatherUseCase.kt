package com.example.impl.domain

import com.example.api.WeatherData
import com.example.api.WeatherRepository
import com.example.local.SharedPrefsManager

class WeatherUseCase(
    private val repository: WeatherRepository,
    private val prefs: SharedPrefsManager
) {
    suspend operator fun invoke(): Result<WeatherData> {
        return repository.getWeather(prefs.getSelectedCity() ?: "Москва")
    }
}