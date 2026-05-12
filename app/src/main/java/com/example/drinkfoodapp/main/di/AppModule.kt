package com.example.drinkfoodapp.main.di

import com.example.drinkfoodapp.main.data.domain.repository.MenuRepository
import com.example.drinkfoodapp.main.data.domain.repository.WineRepository
import com.example.drinkfoodapp.main.data.repository.MenuRepositoryImpl
import com.example.drinkfoodapp.main.data.repository.WineRepositoryImpl
import com.example.drinkfoodapp.main.data.source.local.database.AppDatabase
import com.example.drinkfoodapp.main.data.source.remote.ApiConfig
import com.example.drinkfoodapp.main.ui.home.HomeScreenViewModel
import com.example.drinkfoodapp.main.ui.wine.WineViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { ApiConfig.getApiService() }
    single { AppDatabase.getDatabase(androidContext()) }
    // Wine
    single { get<AppDatabase>().wineDao() }
    single<WineRepository> { WineRepositoryImpl(get(), get()) }
    viewModel { WineViewModel(get()) }
    // Menu
    single { get<AppDatabase>().menuDao() }
    single<MenuRepository> { MenuRepositoryImpl(get()) }
    viewModel { HomeScreenViewModel(get()) }
}
