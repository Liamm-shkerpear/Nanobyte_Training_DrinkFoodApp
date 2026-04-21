package com.example.drinkfoodapp.main.models

object IdGenerator {
    private var currentId = 0
    fun nextId() : Int {
        currentId++
        return currentId
    }
}
