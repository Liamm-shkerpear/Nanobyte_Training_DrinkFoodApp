package com.example.drinkfoodapp.main.ui.wine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import com.example.drinkfoodapp.main.data.domain.repository.WineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WineViewModel(private val repository: WineRepository) : ViewModel() {
    private val _wineItems = MutableStateFlow<List<WineItem>>(emptyList())
    val wineItems = _wineItems.asStateFlow()
    private val _searchItems = MutableStateFlow<List<WineItem>>(emptyList())
    val searchItems = _searchItems.asStateFlow()
    val collectionItems = wineItems.map { items ->
        items.filter { it.isSaved }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            repository.getWines().collect { items ->
                _wineItems.value = items
            }
        }
    }

    fun searchWines(keyword: String) {
        val currentList = _wineItems.value
        if (keyword.isBlank()) {
            _searchItems.value = emptyList()
        } else {
            val filteredList = currentList.filter {
                it.wine?.contains(keyword, ignoreCase = true) == true ||
                        it.winery?.contains(keyword, ignoreCase = true) == true
            }
            _searchItems.value = filteredList
        }
    }

    fun addCollection(wineItem: WineItem) {
        viewModelScope.launch {
            repository.updateState(wineItem.id, !wineItem.isSaved)
        }
    }
}
