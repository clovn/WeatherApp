package com.example.impl

import com.example.api.HourlyForecast
import com.example.api.HourlyWeatherRepository
import com.example.impl.domain.HourlyForecastUseCase
import com.example.local.SharedPrefsManager
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HourlyForecastUseCaseTest {
    private val hourlyWeatherRepository = mockk<HourlyWeatherRepository>()
    private val sharedPrefsManager = mockk<SharedPrefsManager>()

    private lateinit var hourlyForecastUseCase: HourlyForecastUseCase

    @Before
    fun setUp() {
        hourlyForecastUseCase = HourlyForecastUseCase(hourlyWeatherRepository, sharedPrefsManager)
    }

    @Test
    fun `should return hourly forecast for selected city`() = runBlocking {
        val selectedCity = "Санкт-Петербург"
        val mockHourlyForecast = listOf(
            HourlyForecast(time = "10:00", temperature = 15, feelsLike = 12, windSpeed = 1, humidity = 30),
            HourlyForecast(time = "11:00", temperature = 16, feelsLike = 15, windSpeed = 3, humidity = 40)
        )

        coEvery { sharedPrefsManager.getSelectedCity() } returns selectedCity
        coEvery { hourlyWeatherRepository.getHourlyForecast(selectedCity) } returns Result.success(mockHourlyForecast)

        val result = hourlyForecastUseCase.invoke()

        assertTrue(result.isSuccess)
        assertEquals(mockHourlyForecast, result.getOrNull())
    }

    @Test
    fun `should return hourly forecast for default city when no city is selected`() = runBlocking {
        val defaultCity = "Москва"
        val mockHourlyForecast = listOf(
            HourlyForecast(time = "10:00", temperature = 15, feelsLike = 12, windSpeed = 1, humidity = 30),
            HourlyForecast(time = "11:00", temperature = 16, feelsLike = 15, windSpeed = 3, humidity = 40)
        )

        coEvery { sharedPrefsManager.getSelectedCity() } returns null
        coEvery { hourlyWeatherRepository.getHourlyForecast(defaultCity) } returns Result.success(mockHourlyForecast)

        val result = hourlyForecastUseCase.invoke()


        assertTrue(result.isSuccess)
        assertEquals(mockHourlyForecast, result.getOrNull())
    }

    @Test
    fun `should return error when repository fails to fetch hourly forecast`() = runBlocking {
        val selectedCity = "Санкт-Петербург"

        coEvery { sharedPrefsManager.getSelectedCity() } returns selectedCity
        coEvery { hourlyWeatherRepository.getHourlyForecast(selectedCity) } returns Result.failure(
            Exception("Failed to fetch hourly forecast"))

        val result = hourlyForecastUseCase.invoke()

        assertTrue(result.isFailure)
    }

    @Test
    fun `should handle unexpected exceptions during hourly forecast fetch`() = runBlocking {
        val selectedCity = "Санкт-Петербург"

        coEvery { sharedPrefsManager.getSelectedCity() } returns selectedCity
        coEvery { hourlyWeatherRepository.getHourlyForecast(selectedCity) } throws RuntimeException("Unexpected error")

        try {
            hourlyForecastUseCase.invoke()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Unexpected error", e.message)
        }
    }
}