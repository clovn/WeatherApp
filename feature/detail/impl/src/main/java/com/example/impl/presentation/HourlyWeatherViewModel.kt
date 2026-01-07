package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.api.HourlyForecast
import com.example.impl.domain.HourlyForecastUseCase
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class HourlyWeatherViewModel(
    private val hourlyForecast: HourlyForecastUseCase
): ViewModel(), ContainerHost<HourlyForecastState, Nothing> {

    init {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "HourlyForecastScreen")
        }
    }

    override val container = container<HourlyForecastState, Nothing>(HourlyForecastState.Loading)

    fun loadForecast() = intent {
        reduce { HourlyForecastState.Loading }
        try {
            val result = hourlyForecast.invoke()
            reduce { HourlyForecastState.Success(data = result.getOrNull()) }
        } catch (e: Exception) {
            reduce { HourlyForecastState.Error(message = e.message) }
        }
    }
}

sealed class HourlyForecastState{
    object Loading: HourlyForecastState()
    data class Success(val data: List<HourlyForecast>?): HourlyForecastState()
    data class Error(val message: String?): HourlyForecastState()
}