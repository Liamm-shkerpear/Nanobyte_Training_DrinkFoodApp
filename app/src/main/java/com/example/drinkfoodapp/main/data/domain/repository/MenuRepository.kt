package com.example.drinkfoodapp.main.data.domain.repository

import com.example.drinkfoodapp.main.data.domain.entities.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getDrinkItem(): Flow<List<MenuItem>>
    fun getFoodItem(): Flow<List<MenuItem>>

    suspend fun upsertItem(item: MenuItem)
    suspend fun deleteItem(item: MenuItem)
    fun getFavoriteItem(): Flow<List<MenuItem>>
}
