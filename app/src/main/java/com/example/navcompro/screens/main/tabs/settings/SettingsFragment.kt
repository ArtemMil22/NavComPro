package com.example.navcompro.tabs.screens.main.tabs.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navcompro.R
import com.example.navcompro.tabs.Repositories
import com.example.navcompro.databinding.FragmentSettingsBinding
import com.example.navcompro.tabs.utils.viewModelCreator

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    private val viewModel by viewModelCreator { SettingsViewModel(Repositories.boxesRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        val adapter = setupList()
        viewModel.boxSettings.observe(viewLifecycleOwner) { adapter.renderSettings(it) }
    }

    private fun setupList(): SettingsAdapter {
        binding.settingsList.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SettingsAdapter(viewModel)
        binding.settingsList.adapter = adapter
        return adapter
    }

}