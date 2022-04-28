package com.ics342.weatherapp

import android.os.Bundle
import android.view.View
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

        binding.forecastButton.setOnClickListener {
            navigateToForecastFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this) { currentConditions ->
            bindData(currentConditions)
        }

        // This is the ZIP Code and Location Coordinates from searchFragment
        val inputZipCode = args.argStringZipCode
        val passLocationCoordinates = args.argCoordinates

        if (inputZipCode.isNotEmpty()) {
            viewModel.loadData(inputZipCode)
        } else {
            viewModel.loadCoordinatesData(passLocationCoordinates)
        }
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

    private fun navigateToForecastFragment() {
        val passInputZipCode = args.argStringZipCode
        val passCoordinates = args.argCoordinates

        val action =
            CurrentConditionsFragmentDirections.actionCurrentConditionsFragmentToForecastFragment(
                passCoordinates,
                passInputZipCode
            )
        findNavController().navigate(action)
    }

    companion object {
        const val TAG = "CurrentConditionsFragment"
    }
}