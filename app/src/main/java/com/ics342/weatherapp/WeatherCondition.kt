package com.ics342.weatherapp

data class WeatherCondition(
    val main: String,   // Group of weather parameters (Rain, Snow, Extreme etc.)
    val icon: String,   // Weather icon id
)
