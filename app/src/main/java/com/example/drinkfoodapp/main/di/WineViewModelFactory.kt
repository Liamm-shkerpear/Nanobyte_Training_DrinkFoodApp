package com.example.drinkfoodapp.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drinkfoodapp.main.data.domain.repository.WineRepository
import com.example.drinkfoodapp.main.ui.wine.WineViewModel

@Suppress("UNCHECKED_CAST")
class WineViewModelFactory(private val wineRepository: WineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WineViewModel::class.java)) {
            return WineViewModel(wineRepository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: ${modelClass.name}")
    }
}
