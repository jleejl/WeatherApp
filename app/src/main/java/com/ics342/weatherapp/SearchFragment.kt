package com.ics342.weatherapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ics342.weatherapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * This is the Search Fragment Class
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        requireActivity().title = "Search"

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
            val searchInputZipCode = binding.zipCode.text.toString()

            if (foundZipCode) {
                navigateToCurrentCondition(searchInputZipCode)
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

    private fun navigateToCurrentCondition(zipCode: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToCurrentConditionsFragment(
            zipCode
        )
        findNavController().navigate(action)
    }
}
