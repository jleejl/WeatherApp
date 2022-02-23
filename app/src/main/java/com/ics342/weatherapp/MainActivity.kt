package com.ics342.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val apikey = "d68b2cd5f58a3e8473f5921cb7afb26e"

    private lateinit var api: Api
    private lateinit var button: Button
    private lateinit var cityName: TextView
    private lateinit var currentTemp: TextView
    private lateinit var conditionIcon: ImageView
    private lateinit var currentFeelsLike: TextView
    private lateinit var currentTempMin: TextView
    private lateinit var currentTempMax: TextView
    private lateinit var currentPressure: TextView
    private lateinit var currentHumidity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.forecast_button)
        cityName = findViewById(R.id.city_name)
        currentTemp = findViewById(R.id.temperature)
        conditionIcon = findViewById(R.id.condition_icon)
        currentFeelsLike = findViewById(R.id.feels_like)
        currentTempMin = findViewById(R.id.min)
        currentTempMax = findViewById(R.id.max)
        currentPressure = findViewById(R.id.pressure)
        currentHumidity = findViewById(R.id.humidity)

        button.setOnClickListener {
            startActivity(Intent(this, ForecastActivity::class.java))
        }

        /**
         * This is Moshi integration with Retrofit.
         * Retrofit provides a converter for Moshi
         */
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        api = retrofit.create(Api::class.java)
    }

    override fun onResume() {
        super.onResume()

        /**
         * This creates the call object for us, though our API.
         * Using two zipcode here for testing.
         */
        val call: Call<CurrentConditions> = api.getCurrentConditions("96732")   //Kahului

        /**
         * This call the enqueue, puts call on call queue,
         * then we pass the callback object to it
         */
        call.enqueue(object : Callback<CurrentConditions> { // To creating anonymous inner class
            // Two functions use to implement with this, onResponse and onFailure
            override fun onResponse(
                call: Call<CurrentConditions>,
                response: Response<CurrentConditions>
            ) {
                val currentConditions = response.body()
                currentConditions?.let {
                    bindData(it)    // Make null safe way to make API call
                }
            }

            override fun onFailure(call: Call<CurrentConditions>, t: Throwable) {
            }
        })
    }

    private fun bindData(currentConditions: CurrentConditions) {
        cityName.text = currentConditions.name
        currentTemp.text = getString(
            R.string.temperature,
            currentConditions.main.temp.toInt()
        )
        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconUrl = "https://openweathermap.org/img/wn/${iconName}@2x.png"    // Use https
        Glide.with(this)
            .load(iconUrl)
            .into(conditionIcon)

        currentFeelsLike.text =
            getString(R.string.feels_like, currentConditions.main.feelsLike.toInt())
        currentTempMin.text = getString(R.string.low, currentConditions.main.tempMin.toInt())
        currentTempMax.text = getString(R.string.high, currentConditions.main.tempMax.toInt())
        currentPressure.text = getString(R.string.pressure, currentConditions.main.pressure.toInt())
        currentHumidity.text = getString(R.string.humidity, currentConditions.main.humidity.toInt())
    }
}