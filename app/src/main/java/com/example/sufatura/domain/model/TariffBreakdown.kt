package com.example.sufatura.domain.model

import com.example.sufatura.data.local.entities.Tariff

data class TariffBreakdown(
    val tariff: Tariff,      // Tarife bilgisi
    val consumption: Int,    // Bu dilimde harcanan tüketim miktarı
    val cost: Int         // Bu dilimdeki maliyet
)