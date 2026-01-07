package com.example.impl.domain

import com.example.local.SharedPrefsManager

class SaveSelectionUseCase(
    private val prefs: SharedPrefsManager
)
{
    operator fun invoke(city: String){
        prefs.saveSelectedCity(city)
    }
}