package com.acchallenge.rickandmorty.data.repository

import com.acchallenge.rickandmorty.data.ApiService
import com.acchallenge.rickandmorty.data.api.models.characters.CharactersDto
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.SingleCharacterDto
import com.acchallenge.rickandmorty.domain.repository.ServerCharactersRepository
import javax.inject.Inject

class ServerCharactersRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ServerCharactersRepository {
    override suspend fun getCharacters(page: Int, name: String?): CharactersDto {
        return apiService.getCharacters(page, name)
    }

    override suspend fun getCharacterDetails(characterId: Int): SingleCharacterDto {
        return apiService.getCharacterDetails(characterId)
    }
}
