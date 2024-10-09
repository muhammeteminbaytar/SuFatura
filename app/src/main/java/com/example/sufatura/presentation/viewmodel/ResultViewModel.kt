package com.example.sufatura.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sufatura.data.local.SettingsDao
import com.example.sufatura.data.local.CustomerBillDao
import com.example.sufatura.data.local.entities.Tariff
import com.example.sufatura.domain.model.BillResult
import com.example.sufatura.domain.model.TariffBreakdown
import com.example.sufatura.data.local.entities.CustomerBill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val settingsDao: SettingsDao,
    private val customerBillDao: CustomerBillDao // Inject the CustomerBillDao
) : ViewModel() {
    private val _tariffsLiveData = MutableLiveData<List<Tariff>>()
    val tariffsLiveData: LiveData<List<Tariff>> get() = _tariffsLiveData

    // Function to calculate the bill based on the tariffs
    fun calculateBill(currentReading: Int, previousReading: Int, tariffs: List<Tariff>): BillResult {
        val consumption = currentReading - previousReading
        var totalCost = 0.0
        var remainingConsumption = consumption
        val breakdowns = mutableListOf<TariffBreakdown>()

        tariffs.forEach { tariff ->
            if (remainingConsumption <= 0) return@forEach

            val startLimit = tariff.startLimit
            val endLimit = tariff.endLimit ?: Int.MAX_VALUE

            // Calculate applicable consumption
            val applicableConsumption = when {
                remainingConsumption <= 0 -> 0
                startLimit > remainingConsumption -> 0
                else -> {
                    val limit = (endLimit - startLimit).coerceAtLeast(0)
                    remainingConsumption.coerceAtMost(limit)
                }
            }

            if (applicableConsumption > 0) {
                val cost = applicableConsumption * tariff.price
                totalCost += cost

                breakdowns.add(TariffBreakdown(tariff, applicableConsumption, cost))

                remainingConsumption -= applicableConsumption
            }
        }

        return BillResult(consumption, totalCost, breakdowns)
    }

    // Function to get all tariffs from the settings DAO
    fun getAllTariffs() {
        viewModelScope.launch {
            val tariffs = settingsDao.getAllTariffs()
            _tariffsLiveData.postValue(tariffs)
        }
    }

    // New function to save the calculated bill to the database
    fun saveBill(customerNumber: String, meterReading: Int, totalCost: Double) {
        viewModelScope.launch {
            val bill = CustomerBill(
                customerNumber = customerNumber,
                meterReading = meterReading,
                totalCost = totalCost
            )
            customerBillDao.insertBill(bill) // Save the bill to the Room database
        }
    }
}
