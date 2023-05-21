package com.example.softittask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.softittask.data.local.Drink

@Database(entities = [Drink::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drinksDao(): DrinksDao
}