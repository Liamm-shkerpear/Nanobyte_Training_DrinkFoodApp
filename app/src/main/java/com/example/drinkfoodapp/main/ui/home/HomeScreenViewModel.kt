package com.example.drinkfoodapp.main.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkfoodapp.main.data.domain.entities.MenuItem
import com.example.drinkfoodapp.main.data.domain.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: MenuRepository) : ViewModel() {
    private val _drinkItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val drinkItems = _drinkItems.asStateFlow()
    private val _foodItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val foodItems = _foodItems.asStateFlow()
    private val _favoriteItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val favoriteItems = _favoriteItems.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getDrinkItem().collect { items ->
                _drinkItems.value = items
            }
        }
        viewModelScope.launch {
            repository.getFoodItem().collect { items ->
                _foodItems.value = items
            }
        }
        viewModelScope.launch {
            repository.getFavoriteItem().collect { items ->
                _favoriteItems.value = items
            }
        }
    }

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


