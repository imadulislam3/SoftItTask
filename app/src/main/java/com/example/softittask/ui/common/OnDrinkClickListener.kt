package com.example.softittask.ui.common

import com.example.softittask.data.local.Drink

interface OnDrinkClickListener {
    fun onItemClick(drink: Drink)
}