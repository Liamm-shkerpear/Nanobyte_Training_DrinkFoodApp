package com.example.drinkfoodapp.main.data.domain.repository

import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import com.example.drinkfoodapp.main.data.source.local.dao.WineDao
import com.example.drinkfoodapp.main.data.source.remote.ApiConfig
import kotlinx.coroutines.flow.Flow

interface WineRepository {
    fun getWines(): Flow<List<WineItem>>
    fun getCollection(): Flow<List<WineItem>>
    suspend fun updateState(wineId: Int, isSaved: Boolean)
}
