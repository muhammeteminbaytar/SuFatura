package com.example.sufatura.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sufatura.data.local.SettingsDao
import com.example.sufatura.data.local.entities.Tariff
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsDao: SettingsDao
) : ViewModel() {

    fun checkAndInsertDefaultTariffs() {
        viewModelScope.launch {
            if (settingsDao.getTariffCount() == 0) {
                settingsDao.insertTariff(Tariff( startLimit = 0, endLimit =  100, price = 2))
                settingsDao.insertTariff(Tariff( startLimit = 101, endLimit = 500, price = 5))
                settingsDao.insertTariff(Tariff( startLimit = 501, endLimit = -1, price = 10))
            }
        }
    }
}