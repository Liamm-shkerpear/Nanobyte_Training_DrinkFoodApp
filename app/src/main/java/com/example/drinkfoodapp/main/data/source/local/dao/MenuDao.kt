package com.example.drinkfoodapp.main.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.drinkfoodapp.main.data.domain.entities.MenuItem
import kotlinx.coroutines.flow.Flow


@Dao
interface MenuDao {
    @Query("SELECT * FROM table_menu WHERE category = :category")
    fun getMenuItemByCategory(category: String): Flow<List<MenuItem>>
    @Upsert
    suspend fun upsertItem(item: MenuItem)
    @Delete
    suspend fun deleteItem(item: MenuItem)
    @Query("SELECT * FROM table_menu WHERE is_favorite = 1")
    fun getFavoriteItems(): Flow<List<MenuItem>>
}
