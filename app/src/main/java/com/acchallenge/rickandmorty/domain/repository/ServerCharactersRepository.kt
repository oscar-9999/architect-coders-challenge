package com.acchallenge.rickandmorty.domain.repository

import com.acchallenge.rickandmorty.data.api.models.characters.CharactersDto
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.SingleCharacterDto


interface ServerCharactersRepository {

    suspend fun getCharacters(
        page: Int,
        name: String? = null,
    ): CharactersDto

    suspend fun getCharacterDetails(
        characterId: Int,
    ): SingleCharacterDto
}
