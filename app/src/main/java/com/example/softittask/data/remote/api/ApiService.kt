package com.example.softittask.data.remote.api

import com.example.softittask.data.remote.model.Recipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/json/v1/1/search.php")
    fun getRecipes(@Query("s") query: String): Call<Recipes?>

    @GET("api/json/v1/1/search.php")
    fun searchRecipes(@Query("f") query: Char): Call<Recipes?>


}