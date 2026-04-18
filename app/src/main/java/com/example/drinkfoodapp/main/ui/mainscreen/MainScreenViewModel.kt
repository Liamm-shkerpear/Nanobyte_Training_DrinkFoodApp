package com.example.drinkfoodapp.main.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.main.models.MenuItem

class MainViewModel : ViewModel() {
    private val _drinkList = MutableLiveData<List<MenuItem>>(
        listOf(
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
            MenuItem(name = "Cà phê đá", imageResId = R.drawable.capuchino),
        )
    )
    val drinkList: LiveData<List<MenuItem>> = _drinkList

    private val _foodList = MutableLiveData<List<MenuItem>>(
        listOf(
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
            MenuItem(name = "Phở bò", imageResId = R.drawable.pho),
        )
    )
    val foodList: LiveData<List<MenuItem>> = _foodList

    fun addNewItem(name: String, isDrink: Boolean) {
        val defaltImage = if (isDrink) R.drawable.ca_fe else R.drawable.banh_mi
        val newItem = MenuItem(name = name, imageResId = defaltImage)

        if (isDrink) {
            val currentList = _drinkList.value?.toMutableList() ?: mutableListOf()
            currentList.add(newItem)
            _drinkList.value = currentList
        } else {
            val currentList = _foodList.value?.toMutableList() ?: mutableListOf()
            currentList.add(newItem)
            _foodList.value = currentList
        }
    }

    fun deleteItem(item: MenuItem, isDrink: Boolean) {
        if (isDrink) {
            val currentList = _drinkList.value?.toMutableList() ?: mutableListOf()
            currentList.remove(item)
            _drinkList.value = currentList
        } else {
            val currentList = _foodList.value?.toMutableList() ?: mutableListOf()
            currentList.remove(item)
            _foodList.value = currentList
        }
    }

    fun updateItem(oldItem: MenuItem, newName: String, isDrink: Boolean) {
        if (isDrink) {
            val currentList = _drinkList.value?.toMutableList() ?: mutableListOf()
            val index = currentList.indexOf(oldItem)
            if (index != -1) {
                currentList[index] = oldItem.copy(name = newName)
                _drinkList.value = currentList
            }
        } else {
            val currentList = _foodList.value?.toMutableList() ?: mutableListOf()
            val index = currentList.indexOf(oldItem)
            if (index != -1) {
                currentList[index] = oldItem.copy(name = newName)
                _foodList.value = currentList
            }
        }

    }
}
