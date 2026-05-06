package com.example.drinkfoodapp.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.drinkfoodapp.main.data.domain.entities.MenuItem
import com.example.drinkfoodapp.main.data.domain.repository.MenuRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: MenuRepository) : ViewModel() {
    val drinkItems: LiveData<List<MenuItem>> = repository.getDrinkItem().asLiveData()
    val foodItems: LiveData<List<MenuItem>> = repository.getFoodItem().asLiveData()
    val favoriteItems: LiveData<List<MenuItem>> = repository.getFavoriteItem().asLiveData()

    fun saveNewItem(item: MenuItem) {
        viewModelScope.launch {
            repository.upsertItem(item)
        }
    }

    fun handleFavorite(item: MenuItem) {
        viewModelScope.launch {
            val updateItem = item.copy(isFavorite = !item.isFavorite)
            repository.upsertItem(updateItem)
        }
    }

    fun deleteItem(item: MenuItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
}


