package com.ics342.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "d68b2cd5f58a3e8473f5921cb7afb26e",
    ): CurrentConditions

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "d68b2cd5f58a3e8473f5921cb7afb26e",
        // ADD cnt FROM API CALL HERE
        @Query("cnt") cnt: String = "16",   // Display 16 Days of forecast
    ): Forecast

    // Get Current Weather with latitude and longitude
    @GET("weather")
    suspend fun getCurrentConditionsCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "d68b2cd5f58a3e8473f5921cb7afb26e",
    ): CurrentConditions

    // Get Daily Forecast with latitude and longitude
    @GET("forecast/daily")
    suspend fun getForecastCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "d68b2cd5f58a3e8473f5921cb7afb26e",
        @Query("cnt") cnt: String = "16",
    ): Forecast
}