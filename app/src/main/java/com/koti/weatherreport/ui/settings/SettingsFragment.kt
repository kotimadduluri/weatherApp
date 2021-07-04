package com.koti.weatherreport.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.koti.weatherreport.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val settingsViewModel: SettingsViewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        view?.let {
            it.findViewById<CardView>(R.id.help).setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToHelp())
            }
            it.findViewById<CardView>(R.id.delete).setOnClickListener {
                settingsViewModel.deleteAllLocation()
                Toast.makeText(requireContext(),"Deleted all bookmarks", Toast.LENGTH_SHORT).show()
            }
        }
    }
}