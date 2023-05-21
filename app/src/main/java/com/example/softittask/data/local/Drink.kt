package com.example.softittask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drink(
    @PrimaryKey
    val idDrink: String,
    val strAlcoholic: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strInstructions: String,
    var isFav: Int
)