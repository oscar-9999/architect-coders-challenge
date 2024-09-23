package com.acchallenge.rickandmorty.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesContext(context: Application): Context = context.applicationContext

    companion object {
        const val nameApp = "nameApp"
    }
}
