package com.example.network.dto

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("Key")
    val key: String,
    @SerializedName("LocalizedName")
    val localizedName: String
)
