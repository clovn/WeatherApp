package com.example.impl.domain

import com.example.local.SharedPrefsManager

class AddCityUseCase(
    private val prefs: SharedPrefsManager
) {
    operator fun invoke(city: String){
        prefs.addCity(city)
    }
}