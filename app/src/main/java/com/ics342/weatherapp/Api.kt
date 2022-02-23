package com.ics342.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appId") appId: String = "d68b2cd5f58a3e8473f5921cb7afb26e",
    ): Call<CurrentConditions>

    @GET("forecast/daily")
    fun getForecast(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appId") appId: String = "d68b2cd5f58a3e8473f5921cb7afb26e",
        // ADD cnt FROM API CALL HERE
        @Query("cnt") cnt: String = "16",   // Display 16 Days of forecast
    ): Call<Forecast>
}