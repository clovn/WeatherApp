package com.example.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun homeScreenFormat(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM dd")
    return date.format(formatter)
}

fun forecastFormat(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("EEE dd")
    return date.format(formatter)
}

fun hourlyForecastFormat(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm")
    return date.format(formatter)
}
