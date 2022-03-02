package com.ics342.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainViewModel @Inject constructor(private val service: Api) : ViewModel() {

    private val _currentConditions = MutableLiveData<CurrentConditions>()
    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    // Using Kotlin coroutines
    fun loadData() = runBlocking {
        launch {
            // This creates the call object for us, though our API.
            _currentConditions.value = service.getCurrentConditions("96766")    // Lihue, HI, 96766
        }
    }
}