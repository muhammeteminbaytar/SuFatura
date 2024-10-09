package com.example.sufatura.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sufatura.data.local.entities.Tariff

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTariff(tariff: Tariff)

    @Query("SELECT COUNT(*) FROM tariffs")
    suspend fun getTariffCount(): Int

    @Query("SELECT * FROM tariffs")
    suspend fun getAllTariffs(): List<Tariff>
}