package com.example.softittask.di

import android.app.Application
import com.example.softittask.data.remote.api.ApiService
import com.example.softittask.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainRepositoryModule {

    /**
     * @return MainRepository
     * This function gives the implementation of MainRepository to the Hilt
     */
    @Provides
    fun provideMainRepository(apiService: ApiService, application: Application): MainRepository {
        return MainRepository(apiService, application)
    }
}