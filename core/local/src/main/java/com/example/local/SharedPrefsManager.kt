package com.example.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefsManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val SELECTED_CITY_KEY = "selected_city"
        private const val CITIES_LIST_KEY = "cities_list"
    }

    fun saveSelectedCity(city: String) {
        sharedPreferences.edit().putString(SELECTED_CITY_KEY, city).apply()
    }

    fun getSelectedCity(): String? {
        return sharedPreferences.getString(SELECTED_CITY_KEY, null)
    }

    fun saveCitiesList(cities: List<String>) {
        val json = gson.toJson(cities)
        sharedPreferences.edit().putString(CITIES_LIST_KEY, json).apply()
    }

    fun getCitiesList(): List<String> {
        val json = sharedPreferences.getString(CITIES_LIST_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addCity(city: String) {
        val currentList = getCitiesList().toMutableList()
        if (!currentList.contains(city)) {
            currentList.add(city)
            saveCitiesList(currentList)
        }
    }


    fun removeCity(city: String) {
        val currentList = getCitiesList().toMutableList()
        currentList.remove(city)
        saveCitiesList(currentList)

        if (getSelectedCity() == city) {
            saveSelectedCity("")
        }
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}