package com.example.drinkfoodapp.main.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkItem(
    val id: Int = IdGenerator.nextId(),
    val name: String,
    val price: Int,
    val description: String,
    @DrawableRes
    val imageResId: Int

) : Parcelable

