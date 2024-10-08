package com.example.sufatura.domain.model

data class BillResult(
    val consumption: Int,
    val totalCost: Double,
    val tariffBreakdown: List<TariffBreakdown>
)
