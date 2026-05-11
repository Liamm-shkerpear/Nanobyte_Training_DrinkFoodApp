package com.example.drinkfoodapp.main.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WineDao {
    @Query("SELECT * FROM wines")
    fun getAllWines(): Flow<List<WineItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWines(wines: List<WineItem>)

    @Query("SELECT * FROM wines WHERE id = :wineId")
    suspend fun getWineById(wineId: Int): WineItem

    @Query("SELECT * FROM wines WHERE is_saved = 1")
    fun getCollection(): Flow<List<WineItem>>

    @Query("UPDATE wines SET is_saved = :isSaved WHERE id = :wineId")
    suspend fun updateState(wineId: Int, isSaved: Boolean)
}
