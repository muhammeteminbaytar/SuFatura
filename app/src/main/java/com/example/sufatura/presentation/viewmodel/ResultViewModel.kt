package com.example.sufatura.presentation.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.sufatura.domain.model.BillResult
import com.example.sufatura.domain.model.Tariff
import com.example.sufatura.domain.model.TariffBreakdown
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ResultViewModel @Inject constructor() : ViewModel(){


    fun calculateBill(currentReading: Int, previousReading: Int, tariffs: List<Tariff>): BillResult {
        val consumption = currentReading - previousReading
        var totalCost = 0.0
        var remainingConsumption = consumption
        val breakdowns = mutableListOf<TariffBreakdown>()

        for (tariff in tariffs) {
            if (remainingConsumption <= 0) break

            val applicableConsumption = min(remainingConsumption, tariff.limit)
            val cost = applicableConsumption * tariff.price
            totalCost += cost
            breakdowns.add(TariffBreakdown(tariff, applicableConsumption, cost))

            remainingConsumption -= applicableConsumption
        }

        return BillResult(consumption, totalCost, breakdowns)
    }
}