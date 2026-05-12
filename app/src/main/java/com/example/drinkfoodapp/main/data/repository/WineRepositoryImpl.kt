package com.example.drinkfoodapp.main.data.repository

import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import com.example.drinkfoodapp.main.data.domain.repository.WineRepository
import com.example.drinkfoodapp.main.data.source.local.dao.WineDao
import com.example.drinkfoodapp.main.data.source.remote.WineApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class WineRepositoryImpl(
    private val apiService: WineApiService,
    private val wineDao: WineDao
) : WineRepository {
    override fun getWines(): Flow<List<WineItem>> {
        return wineDao.getAllWines()
            .onStart {
                try {
                    val remoteData = apiService.getWines()
                    val localData = wineDao.getAllWines().first()
                    val saveIds = localData.filter { it.isSaved }.map { it.id }.toSet()

                    val dataInsert = remoteData.map { apiItem ->
                        if (saveIds.contains(apiItem.id)) {
                            apiItem.copy(isSaved = true)
                        } else {
                            apiItem
                        }
                    }
                    wineDao.insertWines(dataInsert)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.flowOn(Dispatchers.IO)
    }

    override fun getCollection(): Flow<List<WineItem>> {
        return wineDao.getCollection()
    }

    override suspend fun updateState(wineId: Int, isSaved: Boolean) {
        return wineDao.updateState(wineId, isSaved)
    }
}
