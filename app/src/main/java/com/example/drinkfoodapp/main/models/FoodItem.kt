package com.example.drinkfoodapp.main.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodItem(
    val id: Int = IdGenerator.nextId(),
    val name: String,
    val price: Long,
    val description: String,
    @get:DrawableRes
    val imageResId: Int,
    val isFavorite: Boolean = false
) : Parcelable




