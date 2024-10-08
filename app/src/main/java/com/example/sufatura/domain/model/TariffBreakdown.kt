package com.example.sufatura.domain.model

data class TariffBreakdown(
    val tariff: Tariff,      // Tarife bilgisi
    val consumption: Int,    // Bu dilimde harcanan tüketim miktarı
    val cost: Double         // Bu dilimdeki maliyet
)