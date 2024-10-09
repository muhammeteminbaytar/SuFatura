package com.example.sufatura.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sufatura.data.local.entities.CustomerBill

@Dao
interface CustomerBillDao {
    @Insert
    suspend fun insertBill(bill: CustomerBill)

    @Query("SELECT * FROM customer_bills")
    suspend fun getAllBills(): List<CustomerBill>
}