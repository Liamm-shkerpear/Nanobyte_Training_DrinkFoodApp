package com.example.drinkfoodapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel: ViewModel() {
    // Danh sách (Model)
    private val drinkList = listOf("Cà phê đá", "Trà sữa", "Nước ép cam", "Sinh tố bơ", "Nước dừa", "Trà đào", "Soda chanh", "Cacao nóng", "Trà chanh", "Matcha đá xay", "Nước suối", "Trà vải")
    private val foodList = listOf("Phở bò", "Bánh mì", "Cơm tấm", "Bún chả", "Gỏi cuốn", "Bánh xèo", "Hủ tiếu", "Bún bò Huế", "Xôi mặn", "Bánh bao", "Bánh cuốn", "Cơm chiên")

    // State Index
    private var currentDrinkIndex = 0
    private var currentFoodIndex = 0

    // LiveData để View quan sát
    private val _currentDrink = MutableLiveData<String>()
    val currentDrink: LiveData<String> = _currentDrink

    private val _currentFood = MutableLiveData<String>()
    val currentFood: LiveData<String> = _currentFood

    init {
        updateData()
    }

    fun randomizeItems() {
        currentDrinkIndex = Random.nextInt(drinkList.size)
        currentFoodIndex = Random.nextInt(foodList.size)
        updateData()
    }

    fun randomizeSingleItem(isDrink: Boolean) {
        if (isDrink) {
            currentDrinkIndex = Random.nextInt(drinkList.size)
        } else {
            currentFoodIndex = Random.nextInt(foodList.size)
        }
        updateData()
    }

    fun nextItem(isDrink: Boolean) {
        if (isDrink) {
            currentDrinkIndex = (currentDrinkIndex + 1) % drinkList.size
        } else {
            currentFoodIndex = (currentFoodIndex + 1) % foodList.size
        }
        updateData()
    }

    fun previousItem(isDrink: Boolean) {
        if (isDrink) {
            currentDrinkIndex = (currentDrinkIndex - 1 + drinkList.size) % drinkList.size
        } else {
            currentFoodIndex = (currentFoodIndex - 1 + foodList.size) % foodList.size
        }
        updateData()
    }

    private fun updateData() {
        _currentDrink.value = drinkList[currentDrinkIndex]
        _currentFood.value = foodList[currentFoodIndex]
    }
}