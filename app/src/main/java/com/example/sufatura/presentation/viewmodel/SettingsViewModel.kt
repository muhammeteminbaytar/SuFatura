package com.example.sufatura.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sufatura.data.local.SettingsDao
import com.example.sufatura.data.local.entities.Tariff
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDao: SettingsDao
) : ViewModel() {

    private val _tariffsLiveData = MutableLiveData<List<Tariff>>()
    val tariffsLiveData: LiveData<List<Tariff>> get() = _tariffsLiveData

    fun getAllTariffs() {
        viewModelScope.launch {
            val tariffs = settingsDao.getAllTariffs()
            _tariffsLiveData.postValue(tariffs)
        }
    }
}