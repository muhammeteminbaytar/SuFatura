package com.example.sufatura.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_bills")
data class CustomerBill(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerNumber: String,
    val meterReading: Int,
    val totalCost: Double
)