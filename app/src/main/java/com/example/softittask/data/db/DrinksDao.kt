package com.example.softittask.data.db

import androidx.room.*
import com.example.softittask.data.local.Drink

@Dao
interface DrinksDao {

    @Query("SELECT * FROM Drink")
    fun loadAll(): List<Drink>

    @Query("SELECT * FROM Drink WHERE isFav=1")
    fun loadAllFav(): List<Drink>

    @Query("UPDATE Drink SET isFav = :fav WHERE idDrink = :idDrink")
    fun updateFav(idDrink: String, fav: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrink(favorites: Drink)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDrinkAndIgnore(favorites: Drink)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertDrinks(vararg favorites: List<Drink>)

    @Delete
    fun delete(favorite: Drink?)

    @Query("DELETE FROM Drink WHERE idDrink = :idDrink")
    fun deleteFav(idDrink: String?)
}