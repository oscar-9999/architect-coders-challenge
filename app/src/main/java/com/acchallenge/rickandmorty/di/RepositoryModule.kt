package com.acchallenge.rickandmorty.di

import com.acchallenge.rickandmorty.data.ApiService
import com.acchallenge.rickandmorty.data.repository.CharacterDetailsRepositoryImpl
import com.acchallenge.rickandmorty.data.repository.CharactersRepositoryImpl
import com.acchallenge.rickandmorty.data.repository.ServerCharactersRepositoryImpl
import com.acchallenge.rickandmorty.data.repository.ServerEpisodesImpl
import com.acchallenge.rickandmorty.data.repository.episodes.EpisodesRepositoryImpl
import com.acchallenge.rickandmorty.domain.repository.CharacterDetailsRepository
import com.acchallenge.rickandmorty.domain.repository.CharactersRepository
import com.acchallenge.rickandmorty.domain.repository.ServerEpisodesRepository
import com.acchallenge.rickandmorty.domain.repository.ServerCharactersRepository
import com.acchallenge.rickandmorty.domain.repository.episodes.SingleEpisodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesIRemoteCharactersRepository(apiService: ApiService): ServerCharactersRepository {
        return ServerCharactersRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesIEpisodesRepository(apiService: ApiService): ServerEpisodesRepository {
        return ServerEpisodesImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesCharacterDetailsRepository(repository: ServerCharactersRepository): CharacterDetailsRepository {
        return CharacterDetailsRepositoryImpl(repository)
    }

    @Provides
    @Singleton
    fun providesCharacterRepository(repository: ServerCharactersRepository): CharactersRepository {
        return CharactersRepositoryImpl(repository)
    }

    @Provides
    @Singleton
    fun providesSingleEpisodyRepository(repository: ServerEpisodesRepository): SingleEpisodeRepository {
        return EpisodesRepositoryImpl(repository)
    }
}
