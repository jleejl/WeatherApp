package com.ics342.weatherapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentConditions(
    val weather: List<WeatherCondition>,    // List for weather parameters and icon id
    val main: Currents, // Holds data Currents Class for temp
    val name: String,   // City name
) : Parcelable