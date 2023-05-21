package com.example.softittask.data.remote.model

import android.app.Application
import com.example.softittask.data.db.DbHelper
import com.example.softittask.data.local.Drink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class Recipes(
    val drinks: List<DrinkRemote>
) {
    fun toLocal(context: Application, callBack: (result: Boolean?) -> Unit): List<Drink> {
        val localDrinks = ArrayList<Drink>()
        drinks.forEach {
            localDrinks.add(remoteToLocalDrink(it, context))
        }

        callBack.invoke(true)
        return localDrinks
    }

    private fun remoteToLocalDrink(drinkRemote: DrinkRemote, context: Application): Drink {

        val drink =  Drink(
            idDrink = drinkRemote.idDrink,
            strAlcoholic = drinkRemote.strAlcoholic,
            strDrink = drinkRemote.strDrink,
            strDrinkThumb = drinkRemote.strDrinkThumb,
            strInstructions = drinkRemote.strInstructions,
            isFav = 0
        )

        CoroutineScope(Dispatchers.IO).launch {
            DbHelper.getInstance(context).drinksDao().insertDrinkAndIgnore(drink)
        }

        return drink
    }
}