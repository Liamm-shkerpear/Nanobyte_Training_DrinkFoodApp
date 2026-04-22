package com.example.drinkfoodapp.main.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.main.models.DrinkItem
import com.example.drinkfoodapp.main.models.FoodItem
import com.example.drinkfoodapp.main.models.IdGenerator
import com.example.drinkfoodapp.main.models.SampleData

class MainViewModel : ViewModel() {
    private val _drinkItem = MutableLiveData(SampleData.getDrinkItems())
    val drinkItem: LiveData<List<DrinkItem>> = _drinkItem

    private val _foodItem = MutableLiveData(SampleData.getFoodItems())
    val foodItem: LiveData<List<FoodItem>> = _foodItem


    fun addNewItem(name: String, price: Int, description: String, isDrink: Boolean) {
        val defaultImage = if (isDrink) R.drawable.ca_fe else R.drawable.banh_mi
        if (isDrink) {
            val currentList = _drinkItem.value?.toMutableList() ?: mutableListOf()
            val newItem = DrinkItem(
                id = IdGenerator.nextId(),
                name = name,
                price = price,
                description = description,
                imageResId = defaultImage
            )
            currentList.add(newItem)
            _drinkItem.value = currentList
        } else {
            val currentList = _foodItem.value?.toMutableList() ?: mutableListOf()
            val newItem = FoodItem(
                id = IdGenerator.nextId(),
                name = name,
                price = price,
                description = description,
                imageResId = defaultImage
            )
            currentList.add(newItem)
            _foodItem.value = currentList
        }
    }

    fun deleteItem(item: Any, isDrink: Boolean) {
        when {
            isDrink && item is DrinkItem -> {
                val currentList = _drinkItem.value?.toMutableList() ?: mutableListOf()
                currentList.remove(item)
                _drinkItem.value = currentList
            }

            !isDrink && item is FoodItem -> {
                val currentList = _foodItem.value?.toMutableList() ?: mutableListOf()
                currentList.remove(item)
                _foodItem.value = currentList
            }
        }
    }

    fun updateItem(oldItem: Any, newName: String, newPrice: Int, newDescription: String, isDrink: Boolean) {
        when {
            isDrink && oldItem is DrinkItem -> {
                val currentList = _drinkItem.value?.toMutableList() ?: mutableListOf()
                val index = currentList.indexOf(oldItem)
                if (index != -1) {
                    currentList[index] = oldItem.copy(
                        name = newName,
                        price = newPrice,
                        description = newDescription
                    )
                    _drinkItem.value = currentList
                }
            }

            !isDrink && oldItem is FoodItem -> {
                val currentList = _foodItem.value?.toMutableList() ?: mutableListOf()
                val index = currentList.indexOf(oldItem)
                if (index != -1) {
                    currentList[index] =
                        oldItem.copy(
                            name = newName,
                            price = newPrice,
                            description = newDescription
                        )
                    _foodItem.value = currentList
                }
            }
        }
    }

    fun clearSelection() {
        _selectedItem.value = null
    }

    private val _selectedItem = MutableLiveData<Any?>(null)
    val selectedItem: LiveData<Any?> = _selectedItem

    fun selectItem(item: Any, isDrink: Boolean) {
        when {
            isDrink && item is DrinkItem -> {
                _selectedItem.value = item
            }

            !isDrink && item is FoodItem -> {
                _selectedItem.value = item
            }
        }
    }
}
