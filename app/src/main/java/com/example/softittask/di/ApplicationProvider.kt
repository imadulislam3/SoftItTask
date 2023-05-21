package com.example.softittask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class ApplicationProvider {
    @Provides
    fun provideApplication(@ApplicationContext context: ApplicationContext): ApplicationContext {
        return context
    }

}