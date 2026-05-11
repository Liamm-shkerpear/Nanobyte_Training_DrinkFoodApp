package com.example.drinkfoodapp.main.data.domain.repository

import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import kotlinx.coroutines.flow.Flow

interface WineRepository {
    fun getWines(): Flow<List<WineItem>>
    fun getCollection(): Flow<List<WineItem>>
    suspend fun updateState(wineId: Int, isSaved: Boolean)
}
