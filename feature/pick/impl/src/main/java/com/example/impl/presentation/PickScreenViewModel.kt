package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.impl.domain.LoadCitiesUseCase
import com.example.impl.domain.SaveSelectionUseCase
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class PickScreenViewModel(
    private val saveSelection: SaveSelectionUseCase,
    private val loadCities: LoadCitiesUseCase
) : ViewModel(), ContainerHost<PickState, Nothing> {

    init {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "PickScreen")
        }
    }

    override val container = container<PickState, Nothing>(PickState.Loading)

    fun onCityCardClicked(city: String) = intent {
        saveSelection(city)
    }

    fun loadCities() = intent {
        reduce { PickState.Loading }
        try {
            val result = loadCities.invoke()
            reduce { PickState.Success(cities = result) }
        } catch (e: Exception) {
            reduce { PickState.Error(error = e.message) }
        }
    }
}

sealed class PickState{
    data class Success(val cities: List<String>): PickState()
    object Loading: PickState()
    data class Error(val error: String?): PickState()
}