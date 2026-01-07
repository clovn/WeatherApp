package com.example.impl.domain

import com.example.local.SharedPrefsManager

class LoadCitiesUseCase(
    private val prefs: SharedPrefsManager
) {
    operator fun invoke(): List<String> = prefs.getCitiesList()
}