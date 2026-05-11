package com.example.drinkfoodapp.main.di

import android.content.Context
import com.example.drinkfoodapp.main.data.domain.repository.MenuRepository
import com.example.drinkfoodapp.main.data.domain.repository.WineRepository
import com.example.drinkfoodapp.main.data.repository.MenuRepositoryImpl
import com.example.drinkfoodapp.main.data.repository.WineRepositoryImpl
import com.example.drinkfoodapp.main.data.source.local.database.AppDatabase

object Injection {
    fun provideMenuRepository(context: Context): MenuRepository {
        val database = AppDatabase.getDatabase(context)
        return MenuRepositoryImpl(database.menuDao())
    }
    fun provideWineRepository(context: Context): WineRepository {
        val database = AppDatabase.getDatabase(context)
        return WineRepositoryImpl(database.wineDao())
    }
}
