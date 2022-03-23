package com.ics342.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val service: Api) : ViewModel() {

    private var zipCode: String? = null
    private val _currentConditions = MutableLiveData<CurrentConditions>()

    private val _enableButton = MutableLiveData(false)
    private val _showErrorDialog = MutableLiveData(false)
    private val _zipCodeFound = MutableLiveData(false)

    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog

    val enableButton: LiveData<Boolean>
        get() = _enableButton

    val zipCodeFound: LiveData<Boolean>
        get() = _zipCodeFound

    // Get the ZIP Code in from of a string
    fun updateZipCode(zipCode: String) {
        if (zipCode != this.zipCode)
            this.zipCode = zipCode
        _enableButton.value = isValidZipCode(zipCode)
    }

    // ZIP Code is valid, if total numbers entered is 5 and all digits
    private fun isValidZipCode(zipCode: String): Boolean {
        return zipCode.length == 5 && zipCode.all { it.isDigit() }
    }

    // This is used in function in SearchFragment inside setOnClickListener
    fun submitButtonClicked(zipCode: String) = runBlocking {
        launch {
            try {
                _currentConditions.value = service.getCurrentConditions(zipCode)
                _zipCodeFound.value = true
            } catch (e: retrofit2.HttpException) {
                _showErrorDialog.value = true
            }

            _zipCodeFound.value = false
            _showErrorDialog.value = false
        }
    }
}