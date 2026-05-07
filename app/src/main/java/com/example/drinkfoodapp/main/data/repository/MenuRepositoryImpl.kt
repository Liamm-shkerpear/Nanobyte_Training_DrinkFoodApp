package com.example.drinkfoodapp.main.data.repository

import com.example.drinkfoodapp.main.data.domain.entities.MenuItem
import com.example.drinkfoodapp.main.data.domain.repository.MenuRepository
import com.example.drinkfoodapp.main.data.source.local.dao.MenuDao
import com.example.drinkfoodapp.main.utils.AppConstants
import kotlinx.coroutines.flow.Flow

class MenuRepositoryImpl(private val dao: MenuDao) : MenuRepository {
    override fun getFoodItem(): Flow<List<MenuItem>> {
        return dao.getMenuItemByCategory(AppConstants.FOOD)
    }

    override fun getDrinkItem(): Flow<List<MenuItem>> {
        return dao.getMenuItemByCategory(AppConstants.DRINK)
    }

    override suspend fun upsertItem(item: MenuItem) {
        return dao.upsertItem(item)
    }

    override suspend fun deleteItem(item: MenuItem) {
        return dao.deleteItem(item)
    }

    override fun getFavoriteItem(): Flow<List<MenuItem>> {
        return dao.getFavoriteItems()
    }
}
