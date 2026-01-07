package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.api.LogoutUseCase
import com.example.api.WeatherData
import com.example.impl.domain.WeatherUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class WeatherViewModel(
    private val useCase: WeatherUseCase,
    private val logout: LogoutUseCase
) : ContainerHost<WeatherState, Nothing>, ViewModel() {

    override val container = container<WeatherState, Nothing>(WeatherState.Loading)

    fun onEvent(event: WeatherEvent) {
        when(event){
            is WeatherEvent.LoadWeather -> loadWeather()
            is WeatherEvent.Logout -> logout()
        }
    }

    private fun loadWeather() = intent {
        reduce { WeatherState.Loading }
        try {
            val result = useCase.invoke()
            reduce { WeatherState.Success(data = result.getOrNull()) }
        } catch (e: Exception) {
            reduce { WeatherState.Error(error = e.message) }
        }
    }

    private fun logout() = intent {
        logout.invoke()
        reduce { WeatherState.Logout }
    }
}

sealed class WeatherState {
    data class Success(val data: WeatherData? = null) : WeatherState()
    object Loading : WeatherState()
    data class Error(val error: String?): WeatherState()
    object Logout: WeatherState()
}

sealed class WeatherEvent {
    object LoadWeather: WeatherEvent()
    object Logout: WeatherEvent()
}