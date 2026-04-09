package com.example.drinkfoodapp.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkfoodapp.R
import kotlin.random.Random
import com.example.drinkfoodapp.main.model.MenuItem

class MainViewModel: ViewModel() {
    // Danh sách (Model)
    //private val drinkList = listOf("Cà phê đá", "Trà sữa", "Nước ép cam", "Sinh tố bơ", "Nước dừa", "Trà đào", "Soda chanh", "Cacao nóng", "Trà chanh", "Matcha đá xay", "Nước suối", "Trà vải")
    //private val foodList = listOf("Phở bò", "Bánh mì", "Cơm tấm", "Bún chả", "Gỏi cuốn", "Bánh xèo", "Hủ tiếu", "Bún bò Huế", "Xôi mặn", "Bánh bao", "Bánh cuốn", "Cơm chiên")
    private val drinkList = listOf(
        MenuItem("Cà phê đá", R.drawable.ca_fe),
        MenuItem("Trà sữa", R.drawable.tra_sua),
        MenuItem("Nước ép cam", R.drawable.nuoc_cam), //ok
        MenuItem("Sinh tố bơ", R.drawable.sinh_to_bo),
    )

    private val foodList = listOf(
        MenuItem("Phở bò", R.drawable.pho),
        MenuItem("Bánh mì", R.drawable.banh_mi),
        MenuItem("Cơm tấm", R.drawable.com_tam),
        MenuItem("Bún chả", R.drawable.bun_cha),
    )
    // State Index
    private var currentDrinkIndex = 0
    private var currentFoodIndex = 0

    // LiveData để View quan sát
    private val _currentDrink = MutableLiveData<MenuItem>()
    val currentDrink: LiveData<MenuItem> = _currentDrink

    private val _currentFood = MutableLiveData<MenuItem>()
    val currentFood: LiveData<MenuItem> = _currentFood

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