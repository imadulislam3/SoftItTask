package com.example.softittask.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.softittask.data.db.DbHelper
import com.example.softittask.data.local.Drink
import com.example.softittask.data.remote.api.ApiService
import com.example.softittask.data.remote.model.Recipes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private var apiService: ApiService,
    private var application: Application
) {

    private val recipesResponseLiveData: MutableLiveData<List<Drink>> by lazy { MutableLiveData<List<Drink>>() }
    private val favoriteLiveData: MutableLiveData<List<Drink>> by lazy { MutableLiveData<List<Drink>>() }

    fun getRemoteRecipes(recipe: String) {
        apiService.getRecipes(recipe)
            .enqueue(object :
                Callback<Recipes?> {
                override fun onResponse(
                    call: Call<Recipes?>,
                    response: Response<Recipes?>
                ) {
                    if (response.body() != null) {

                        response.body()?.toLocal(application) {
                            getLocalDrinks()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<Recipes?>,
                    t: Throwable
                ) {
                    recipesResponseLiveData.postValue(null)
                    t.printStackTrace()
                }
            })
    }

    fun getRemoteRecipes(recipe: Char) {
        apiService.searchRecipes(recipe)
            .enqueue(object :
                Callback<Recipes?> {
                override fun onResponse(
                    call: Call<Recipes?>,
                    response: Response<Recipes?>
                ) {
                    if (response.body() != null) {

                        response.body()?.toLocal(application) {
                            getLocalDrinks()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<Recipes?>,
                    t: Throwable
                ) {
                    recipesResponseLiveData.postValue(null)
                    t.printStackTrace()
                }
            })
    }

    fun getRecipes(): LiveData<List<Drink>> {
        return recipesResponseLiveData.value.let { recipesResponseLiveData }
    }

    fun getLocalDrinks() {
        CoroutineScope(Dispatchers.IO).launch {
            recipesResponseLiveData.postValue(
                DbHelper.getInstance(application).drinksDao().loadAll()
            )
        }
    }

    fun getFavoriteDrinks() {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteLiveData.postValue(DbHelper.getInstance(application).drinksDao().loadAllFav())
        }
    }

    fun getFavoriteRecipes(): LiveData<List<Drink>> {
        return favoriteLiveData.value.let { favoriteLiveData }
    }
}