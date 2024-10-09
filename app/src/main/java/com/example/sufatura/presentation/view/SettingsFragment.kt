package com.example.sufatura.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sufatura.R
import com.example.sufatura.databinding.FragmentSettingsBinding
import com.example.sufatura.presentation.adapter.TariffAdapter
import com.example.sufatura.presentation.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {
    private lateinit var settingsFragmentBinding: FragmentSettingsBinding
    val viewModel: SettingsViewModel by activityViewModels()
    private lateinit var adapter: TariffAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingsFragmentBinding = FragmentSettingsBinding.inflate(inflater, container, false)

        viewModel.getAllTariffs()

        viewModel.tariffsLiveData.observe(viewLifecycleOwner) { tariffs ->
            adapter = TariffAdapter(tariffs)
            settingsFragmentBinding.rvSetting.adapter = adapter
            settingsFragmentBinding.rvSetting.layoutManager = LinearLayoutManager(context)
        }

        return settingsFragmentBinding.root
    }
}