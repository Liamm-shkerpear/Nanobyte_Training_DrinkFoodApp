package com.example.drinkfoodapp.main.data.domain.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_menu")
data class MenuItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Long,
    @get:DrawableRes
    val imageResId: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    val description: String,
    val category: String
) : Parcelable
