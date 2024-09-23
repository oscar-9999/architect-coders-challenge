package com.acchallenge.rickandmorty.domain.repository

import com.acchallenge.rickandmorty.domain.model.CharacterDetails

interface CharacterDetailsRepository {
    suspend fun getCharacterDetails(id: Int): Result<CharacterDetails>
}
