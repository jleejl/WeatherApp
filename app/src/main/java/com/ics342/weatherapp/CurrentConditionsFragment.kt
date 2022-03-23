package com.ics342.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ics342.weatherapp.databinding.FragmentCurrentConditionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * This is the Current Conditions Fragment Class
 */
@AndroidEntryPoint
class CurrentConditionsFragment : Fragment(R.layout.fragment_current_conditions) {

    private val apikey = "d68b2cd5f58a3e8473f5921cb7afb26e"
    private val args: CurrentConditionsFragmentArgs by navArgs()
    private lateinit var binding: FragmentCurrentConditionsBinding

    @Inject
    lateinit var viewModel: CurrentConditionsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Display the information/layout
        binding = FragmentCurrentConditionsBinding.bind(view)

        // Set title of an Activity owned AppBar from a fragment
        requireActivity().title = "Current Conditions"

        /*
          ZIP Code stored here to be pass on to next Forecast Fragment when
          button is clicked
          */
        val passInputZipCode = args.argStringZipCode

        // Call button to navigate to Forecast Fragment
        binding.forecastButton.setOnClickListener {
            navigateToForecastFragment(passInputZipCode)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this) { currentConditions ->
            bindData(currentConditions)
        }
        val inputZipCode = args.argStringZipCode
        /*
         Takes the ZIP Code here and pass it to CurrentConditionsViewModel Class
         function loadData()
         */
        viewModel.loadData(inputZipCode)
    }

    private fun bindData(currentConditions: CurrentConditions) {
        binding.cityName.text = currentConditions.name
        binding.temperature.text = getString(
            R.string.temperature,
            currentConditions.main.temp.toInt()
        )
        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconUrl = "https://openweathermap.org/img/wn/${iconName}@2x.png"    // Use https
        Glide.with(this)
            .load(iconUrl)
            .into(binding.conditionIcon)

        binding.feelsLike.text =
            getString(R.string.feels_like, currentConditions.main.feelsLike.toInt())
        binding.min.text = getString(R.string.low, currentConditions.main.tempMin.toInt())
        binding.max.text = getString(R.string.high, currentConditions.main.tempMax.toInt())
        binding.pressure.text =
            getString(R.string.pressure, currentConditions.main.pressure.toInt())
        binding.humidity.text =
            getString(R.string.humidity, currentConditions.main.humidity.toInt())
    }

    private fun navigateToForecastFragment(zipCode: String) {
        val action = CurrentConditionsFragmentDirections
            .actionCurrentConditionsFragmentToForecastFragment(zipCode)
        findNavController().navigate(action)
    }

    companion object {
        const val TAG = "CurrentConditionsFragment"
    }
}