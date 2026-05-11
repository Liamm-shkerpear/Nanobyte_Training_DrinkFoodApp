package com.example.drinkfoodapp.main.data.source.remote

import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import retrofit2.http.GET

interface WineApiService {
    @GET("wines/reds")
    suspend fun getWines(): List<WineItem>
}
