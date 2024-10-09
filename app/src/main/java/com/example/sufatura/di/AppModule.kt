package com.example.sufatura.di

import android.content.Context
import androidx.room.Room
import com.example.sufatura.data.local.AppDatabase
import com.example.sufatura.data.local.SettingsDao
import com.example.sufatura.data.local.CustomerBillDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSettingsDao(database: AppDatabase): SettingsDao {
        return database.settingsDao()
    }

    // New DAO provider for CustomerBillDao
    @Provides
    fun provideCustomerBillDao(database: AppDatabase): CustomerBillDao {
        return database.customerBillDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
