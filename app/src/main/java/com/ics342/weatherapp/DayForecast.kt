package com.ics342.weatherapp

import com.squareup.moshi.Json

data class DayForecast(
    @Json(name = "dt") val date: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: ForecastTemp,
    val pressure: Float,    // NOT USING THIS
    val humidity: Int,   // NOT USING THIS
    val weather: List<WeatherCondition>,
)