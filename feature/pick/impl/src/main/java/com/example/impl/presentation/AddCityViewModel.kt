package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.impl.domain.AddCityUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class AddCityViewModel(
    private val addCity: AddCityUseCase
): ViewModel(), ContainerHost<AddCityState, Nothing> {

    override val container = container<AddCityState, Nothing>(AddCityState.Idle)

    fun addCity(city: String) = intent {
        try {
            addCity.invoke(city)
            reduce { AddCityState.NavigateToPick }
        } catch (e: Exception) {
            reduce { AddCityState.Error(error = e.message) }
        }
    }
}

sealed class AddCityState{
    object Idle: AddCityState()
    data class Error(val error: String?): AddCityState()
    object NavigateToPick: AddCityState()
}