package com.acchallenge.rickandmorty.data.repository

import com.acchallenge.rickandmorty.data.mappers.toCharacter
import com.acchallenge.rickandmorty.data.utils.BaseRepository
import com.acchallenge.rickandmorty.domain.model.CharacterDetails
import com.acchallenge.rickandmorty.domain.repository.CharacterDetailsRepository
import com.acchallenge.rickandmorty.domain.repository.ServerCharactersRepository
import javax.inject.Inject

class CharacterDetailsRepositoryImpl @Inject constructor(
    private val repository: ServerCharactersRepository,
) : CharacterDetailsRepository, BaseRepository() {
    override suspend fun getCharacterDetails(id: Int): Result<CharacterDetails> {
        return safeApiCall {
            repository.getCharacterDetails(id).toCharacter()
        }
    }
}
