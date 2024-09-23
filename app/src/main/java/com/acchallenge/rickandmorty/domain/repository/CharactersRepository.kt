package com.acchallenge.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getAllCharacters(name: String? = null): Flow<PagingData<CharacterItem>>
}
