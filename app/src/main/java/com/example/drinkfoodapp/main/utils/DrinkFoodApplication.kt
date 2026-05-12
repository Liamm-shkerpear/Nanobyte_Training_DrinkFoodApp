package com.example.drinkfoodapp.main.utils

import android.app.Application
import com.example.drinkfoodapp.main.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
class DrinkFoodApplication : Application(), KoinStartup {
    override fun onKoinStartup() = KoinConfiguration {
        androidLogger()
        androidContext(this@DrinkFoodApplication)
        modules(appModule)
    }
}
