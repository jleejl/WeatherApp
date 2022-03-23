package com.ics342.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ics342.weatherapp.databinding.FragmentForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * This is the Forecast Fragment Class
 */
@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.fragment_forecast) {

    private val apikey = "d68b2cd5f58a3e8473f5921cb7afb26e"
    private val args: ForecastFragmentArgs by navArgs()

    // Using view binding
    private lateinit var binding: FragmentForecastBinding

    @Inject
    lateinit var viewModel: ForecastViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Forecast"

        binding = FragmentForecastBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

    override fun onResume() {
        super.onResume()
        viewModel.forecast.observe(this) { forecast ->
            bindData(forecast)
        }
        viewModel.loadData(args.argStringZipCode)
    }

    private fun bindData(forecast: Forecast) {
        binding.recyclerView.adapter = MyAdapter(forecast.list)
    }

    companion object {
        const val TAG = "ForecastFragment"
    }
}