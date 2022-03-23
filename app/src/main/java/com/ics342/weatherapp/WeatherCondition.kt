package com.ics342.weatherapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherCondition(
    val main: String,   // Group of weather parameters (Rain, Snow, Extreme etc.)
    val icon: String,   // Weather icon id
) : Parcelable
