package com.ics342.weatherapp

data class DayForecast(
    val date: Long,
    //val sunrise: Long,    // THIS IS FOR NEXT ASSIGNMENT
    //val sunset: Long, // THIS IS FOR NEXT ASSIGNMENT
    //val temp: Long,  // THIS IS FOR NEXT ASSIGNMENT, WILL COME BACK TO, temp:ForecastTemp
    //val min: Float, // THIS IS FOR NEXT ASSIGNMENT
    //val max: Float  // THIS IS FOR NEXT ASSIGNMENT
    //val pressure: Float,  // This is not needed yet, THIS IS FOR NEXT ASSIGNMENT
    //val humidity: Int)   // This is not needed yet, THIS IS FOR NEXT ASSIGNMENT

    val sunrise: String,    // Used this for hard coded
    val sunset: String, // Used this for hard coded
    val temp: String,   // Used this for hard coded
    val high: String,   // Used this for hard coded
    val low: String // Used this for hard coded
) {

}