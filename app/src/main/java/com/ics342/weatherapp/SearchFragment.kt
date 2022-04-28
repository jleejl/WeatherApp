package com.ics342.weatherapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.ics342.weatherapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
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

    @RequiresApi(Build.VERSION_CODES.O)
    private val requestPermissionResultNotification =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.notificationsButton.text = "Turn off notifications"

                val text = "Location Permission Granted"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)

                toast.show()
                startService()
            } else {
                showLocationPermissionRationale()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
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

        // Notification button
        binding.notificationsButton.setOnClickListener {
            if (binding.notificationsButton.text == "Turn on notifications") {

                // Checks for the permission when button is clicked
                if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {

                    requestPermissionResultNotification.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                } else {
                    binding.notificationsButton.text = "Turn off notifications"
                    startService()
                }

            } else {
                binding.notificationsButton.text = "Turn on notifications"
                stopService()
            }
        }
    }

    // Start service
    @RequiresApi(Build.VERSION_CODES.O)
    private fun startService() {
        val intent = Intent(context, MyService::class.java)
        requireActivity().startForegroundService(intent)
    }

    // Stop service
    private fun stopService() {
        val intent = Intent(context, MyService::class.java)
        requireActivity().stopService(intent)
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