package com.example.drinkfoodapp.main.models

import androidx.annotation.DrawableRes

data class MenuItem(
    val id: Int = nextId(),
    val name: String,
    @DrawableRes val imageResId: Int,
) {
    companion object {
        var currentId = 0
        fun nextId(): Int {
            currentId ++
            return currentId
        }
    }
}


