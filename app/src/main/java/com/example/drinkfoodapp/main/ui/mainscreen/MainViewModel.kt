package com.example.drinkfoodapp.main.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.main.model.MenuItem
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val drinkList = listOf(
        MenuItem("Cà phê đá", R.drawable.ca_fe),
        MenuItem("Trà sữa", R.drawable.tra_sua),
        MenuItem("Nước ép cam", R.drawable.nuoc_cam),
        MenuItem("Sinh tố bơ", R.drawable.sinh_to_bo),
    )

    private val foodList = listOf(
        MenuItem("Phở bò", R.drawable.pho),
        MenuItem("Bánh mì", R.drawable.banh_mi),
        MenuItem("Cơm tấm", R.drawable.com_tam),
        MenuItem("Bún chả", R.drawable.bun_cha),
    )
    private var currentDrinkIndex = 0
    private var currentFoodIndex = 0

    private val _currentDrink = MutableLiveData<MenuItem>()
    val currentDrink: LiveData<MenuItem> = _currentDrink

    private val _currentFood = MutableLiveData<MenuItem>()
    val currentFood: LiveData<MenuItem> = _currentFood

    init {
        updateData()
    }

    fun randomizeItems() {
        currentDrinkIndex = Random.Default.nextInt(drinkList.size)
        currentFoodIndex = Random.Default.nextInt(foodList.size)
        updateData()
    }

    fun randomizeSingleItem(isDrink: Boolean) {
        if (isDrink) {
            currentDrinkIndex = Random.Default.nextInt(drinkList.size)
        } else {
            currentFoodIndex = Random.Default.nextInt(foodList.size)
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
