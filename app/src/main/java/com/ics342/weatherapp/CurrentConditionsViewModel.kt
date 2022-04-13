package com.ics342.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CurrentConditionsViewModel @Inject constructor(private val service: Api) : ViewModel() {

    private val _currentConditions = MutableLiveData<CurrentConditions>()

    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    // Using Kotlin coroutines
    fun loadData(zipCode: String) = runBlocking {
        launch {
            // This creates the call object for us, though our API.
            _currentConditions.value = service.getCurrentConditions(zipCode)
        }
    }

    // Coordinates
    fun loadCoordinatesData(coordinates: Coordinates) = runBlocking {
        launch {
            _currentConditions.value = service.getCurrentConditionsCoordinates(
                coordinates.lat.toString(),
                coordinates.lon.toString()
            )
        }
    }
}
