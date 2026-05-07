package com.example.drinkfoodapp.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drinkfoodapp.main.data.domain.repository.MenuRepository
import com.example.drinkfoodapp.main.ui.home.HomeScreenViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: MenuRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
