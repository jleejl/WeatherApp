package com.ics342.weatherapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.DeadObjectException
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.UnusedAppRestrictionsConstants
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.ics342.weatherapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest
import javax.inject.Inject

/**
 * This is the Search Fragment Class
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),
    ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                showLocationPermissionRationale()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        requireActivity().title = "Search"

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.requestLocationButton.setOnClickListener {
            requestPermissionResult.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        searchViewModel.enableButton.observe(viewLifecycleOwner) { enable ->
            binding.searchButton.isEnabled = enable

            // User ZIP Code input
            val inputZipCode = binding.zipCode.text.toString()

            if (enable) {
                binding.searchButton.setOnClickListener {
                    searchViewModel.submitButtonClicked(inputZipCode)
                }
            }
        }

        searchViewModel.showErrorDialog.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                ErrorDialogFragment().show(childFragmentManager, ErrorDialogFragment.TAG)
            }
        }

        searchViewModel.zipCodeFound.observe(viewLifecycleOwner) { foundZipCode ->
            if (foundZipCode) {
                navigateToCurrentCondition()
            }
        }

        binding.zipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let { searchViewModel.updateZipCode(it) }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun navigateToCurrentCondition() {
        var searchInputZipCode = binding.zipCode.text.toString()
        var lat = latitude
        var long = longitude

        if (searchInputZipCode.isNotEmpty() || searchInputZipCode.isNotBlank()) {
            searchInputZipCode = searchInputZipCode
            lat = 0.0
            long = 0.0
        }

        if (searchInputZipCode.isEmpty() || searchInputZipCode.isBlank()) {
            lat = latitude
            long = longitude
        }

        val coord = Coordinates(lat, long)
        val action = SearchFragmentDirections.actionSearchFragmentToCurrentConditionsFragment(
            coord,
            searchInputZipCode
        )
        findNavController().navigate(action)
    }

    private fun showLocationPermissionRationale() {
        AlertDialog.Builder(requireActivity())
            .setMessage(R.string.location_permission_rationale)
            .setNeutralButton(R.string.location_permission_rationale_button) { _, _ ->
            }.create().show()
    }

    private fun showLocationNotFoundDialog() {
        AlertDialog.Builder(requireActivity())
            .setMessage(R.string.location_not_found)
            .setNeutralButton(R.string.location_not_found_button) { _, _ ->
            }.create().show()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null) {
                Toast.makeText(context, "Can Not Get Location.", Toast.LENGTH_SHORT).show()
                showLocationNotFoundDialog()
            } else {
                Toast.makeText(context, "Location Found.", Toast.LENGTH_SHORT).show()

                latitude = location?.latitude
                longitude = location?.longitude

                navigateToCurrentCondition()
            }
        }
    }
}