package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.impl.domain.AddCityUseCase
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class AddCityViewModel(
    private val addCity: AddCityUseCase
): ViewModel(), ContainerHost<AddCityState, Nothing> {

    init {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "AddCityScreen")
        }
    }

    override val container = container<AddCityState, Nothing>(AddCityState.Idle())

    fun onEvent(event: AddCityEvent) {
        when(event){
            is AddCityEvent.AddCity -> addCity(event.city)
            is AddCityEvent.ChangeCity -> changeCity(event.city)
        }
    }

    private fun changeCity(city: String) = intent {
        reduce { AddCityState.Idle(cityName = city) }
    }

    private fun addCity(city: String) = intent {
        try {
            addCity.invoke(city)
            reduce { AddCityState.NavigateToPick }
        } catch (e: Exception) {
            reduce { AddCityState.Error(error = e.message) }
        }
    }
}

sealed class AddCityState{
    data class Idle(val cityName: String = ""): AddCityState()
    data class Error(val error: String?): AddCityState()
    object NavigateToPick: AddCityState()
}

sealed class AddCityEvent{
    data class AddCity(val city: String): AddCityEvent()
    data class ChangeCity(val city: String): AddCityEvent()
}