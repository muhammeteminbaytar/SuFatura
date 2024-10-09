package com.example.sufatura.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sufatura.data.local.entities.CustomerBill
import com.example.sufatura.data.local.entities.Tariff

@Database(entities = [Tariff::class, CustomerBill::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao
    abstract fun customerBillDao(): CustomerBillDao
}