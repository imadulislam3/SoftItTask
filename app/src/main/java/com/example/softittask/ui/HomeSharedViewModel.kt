package com.example.softittask.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.softittask.data.local.Drink
import com.example.softittask.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSharedViewModel @Inject constructor(
    private var mainRepository: MainRepository,
    application: Application
) : AndroidViewModel(application) {
    private var recipesLiveData: LiveData<List<Drink>> =
        mainRepository.getRecipes()

    private var favoriteLiveData: LiveData<List<Drink>> =
        mainRepository.getFavoriteRecipes()

    fun getRecipes(recipe: String) {
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.getRemoteRecipes(recipe)
        }
    }

    fun getRecipes(recipe: Char) {
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.getRemoteRecipes(recipe)
        }
    }

    fun getRecipesLiveData(): LiveData<List<Drink>> {
        return recipesLiveData
    }

    fun getFavoritesLiveData(): LiveData<List<Drink>> {
        return favoriteLiveData
    }

    fun getLocalFavorites(){
        mainRepository.getFavoriteDrinks()
    }

}
