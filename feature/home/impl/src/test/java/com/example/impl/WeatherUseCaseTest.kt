package com.example.impl

import com.example.api.WeatherData
import com.example.api.WeatherRepository
import com.example.impl.domain.WeatherUseCase
import com.example.local.SharedPrefsManager
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WeatherUseCaseTest {
    private val weatherRepository = mockk<WeatherRepository>()
    private val sharedPrefsManager = mockk<SharedPrefsManager>()

    private lateinit var weatherUseCase: WeatherUseCase

    @Before
    fun setUp() {
        weatherUseCase = WeatherUseCase(weatherRepository, sharedPrefsManager)
    }

    @Test
    fun `should return weather data for selected city`() = runBlocking {
        val selectedCity = "Санкт-Петербург"
        val mockWeatherData = WeatherData(
            city = selectedCity,
            date = "2026-01-07",
            weatherIconUrl = "https://example.com/icon.png",
            weatherText = "Cloudy",
            temperature = 5.0,
            humidity = 80.0,
            windSpeed = 10.0,
            feelsLike = 3.0,
            forecast = emptyList(),
            photoURL = "https://example.com/photo.jpg"
        )

        coEvery { sharedPrefsManager.getSelectedCity() } returns selectedCity
        coEvery { weatherRepository.getWeather(selectedCity) } returns Result.success(mockWeatherData)

        val result = weatherUseCase.invoke()

        assertEquals(mockWeatherData, result.getOrNull())
    }

    @Test
    fun `should return weather data for default city when no city is selected`() = runBlocking {
        val defaultCity = "Москва"
        val mockWeatherData = WeatherData(
            city = defaultCity,
            date = "2026-01-07",
            weatherIconUrl = "https://example.com/icon.png",
            weatherText = "Sunny",
            temperature = 10.0,
            humidity = 60.0,
            windSpeed = 5.0,
            feelsLike = 9.0,
            forecast = emptyList(),
            photoURL = "https://example.com/photo.jpg"
        )

        coEvery { sharedPrefsManager.getSelectedCity() } returns null
        coEvery { weatherRepository.getWeather(defaultCity) } returns Result.success(mockWeatherData)

        val result = weatherUseCase.invoke()

        assertEquals(true, result.isSuccess)
        assertEquals(mockWeatherData, result.getOrNull())
    }

    @Test
    fun `should return error when repository fails to fetch weather data`() = runBlocking {
        val selectedCity = "Санкт-Петербург"

        coEvery { sharedPrefsManager.getSelectedCity() } returns selectedCity
        coEvery { weatherRepository.getWeather(selectedCity) } returns Result.failure(Exception("Failed to fetch weather data"))

        val result = weatherUseCase.invoke()

        assertEquals(true, result.isFailure)
    }

    @Test
    fun `should handle unexpected exceptions during weather fetch`() = runBlocking {
        val selectedCity = "Санкт-Петербург"

        coEvery { sharedPrefsManager.getSelectedCity() } returns selectedCity
        coEvery { weatherRepository.getWeather(selectedCity) } throws RuntimeException("Unexpected error")

        try {
            weatherUseCase.invoke()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Unexpected error", e.message)
        }
    }
}