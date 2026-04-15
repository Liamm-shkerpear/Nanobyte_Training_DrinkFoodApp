package com.example.drinkfoodapp.main.model

import androidx.annotation.DrawableRes

data class MenuItem(
    val name: String,
    @DrawableRes val imageResId: Int,
)

