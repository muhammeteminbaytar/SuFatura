package com.example.sufatura.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tariffs")
data class Tariff(
    @PrimaryKey(autoGenerate = true) val tariffId: Int = 0,
    val startLimit: Int,
    val endLimit: Int?,
    val price: Int
)
