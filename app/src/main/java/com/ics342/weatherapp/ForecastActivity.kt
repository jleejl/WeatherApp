package com.ics342.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ForecastActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val apikey = "d68b2cd5f58a3e8473f5921cb7afb26e"

    private lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

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
        val call: Call<Forecast> = api.getForecast("96732")   // Kahului

        /**
         * This call the enqueue, puts call on call queue,
         * then we pass the callback object to it
         */
        call.enqueue(object : Callback<Forecast> {  // This is creating anonymous inner class
            // Two functions use to implement with this, onResponse and onFailure
            override fun onResponse(
                call: Call<Forecast>,
                response: Response<Forecast>
            ) {
                val forecast = response.body()
                forecast?.let {
                    bindData(it)    // We do this to make null safe way to make API call
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
            }
        })
    }

    /**
     * PART 2,
     * Create our adapter and pass the data that we got back from the server.
     */
    private fun bindData(forecast: Forecast) {
        recyclerView.adapter = MyAdapter(forecast.list)
    }
}