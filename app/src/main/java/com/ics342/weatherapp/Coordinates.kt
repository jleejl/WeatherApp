package com.ics342.weatherapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates (
    val lat: Double,
    val lon: Double
) : Parcelable

