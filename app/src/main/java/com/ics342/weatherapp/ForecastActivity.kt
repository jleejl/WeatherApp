package com.ics342.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ics342.weatherapp.databinding.ActivityForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * This ForecastActivity Class is operating as a View.
 */
@AndroidEntryPoint
class ForecastActivity : AppCompatActivity() {

    private val apikey = "d68b2cd5f58a3e8473f5921cb7afb26e"

    // Using view binding
    private lateinit var binding: ActivityForecastBinding
    @Inject
    lateinit var viewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.forecast.observe(this) { forecast ->
            bindData(forecast)
        }
        viewModel.loadData()
    }

    /**
     * PART 2,
     * Create our adapter and pass the data that we got back from the server.
     */
    private fun bindData(forecast: Forecast) {
        binding.recyclerView.adapter = MyAdapter(forecast.list)
    }
}