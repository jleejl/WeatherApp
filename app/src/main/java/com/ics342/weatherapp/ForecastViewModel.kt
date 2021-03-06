package com.ics342.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service: Api) : ViewModel() {

    private val _forecast: MutableLiveData<Forecast> = MutableLiveData()

    val forecast: LiveData<Forecast>
        get() = _forecast

    // Using Kotlin coroutines
    fun loadData(zipCode: String) = runBlocking {
        launch {
            //This creates the call object for us, though our API.
            _forecast.value = service.getForecast(zipCode)
        }
    }

    // Coordinates
    fun loadCoordinatesData(coordinates: Coordinates) = runBlocking {
        launch {
            _forecast.value = service.getForecastCoordinates(
                coordinates.lat.toString(),
                coordinates.lon.toString()
            )
        }
    }
}