package com.acchallenge.rickandmorty.appTestShared.fakedata

import android.content.res.Resources
import com.acchallenge.rickandmorty.data.api.models.characters.CharactersDto
import com.acchallenge.rickandmorty.data.api.models.characters.ResultDto
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.SingleCharacterDto
import com.acchallenge.rickandmorty.data.mappers.toSingleCharacter
import com.acchallenge.rickandmorty.domain.repository.ServerCharactersRepository
import com.google.gson.Gson

class FakeCharactersRepository : ServerCharactersRepository {

    private val charactersPages: MutableMap<Int, CharactersDto> = mutableMapOf()

    private var allCharacters: List<ResultDto>

    init {
        val gson = Gson()
        val page1CharactersDto = gson.fromJson(charactersPage1Json, CharactersDto::class.java)
        val page2CharactersDto = gson.fromJson(charactersPage2Json, CharactersDto::class.java)
        val page3CharactersDto = gson.fromJson(charactersPage3Json, CharactersDto::class.java)

        charactersPages[1] = page1CharactersDto
        charactersPages[2] = page2CharactersDto
        charactersPages[3] = page3CharactersDto

        allCharacters = buildList {
            page1CharactersDto.results
            page2CharactersDto.results
            page3CharactersDto.results
        }
    }

    override suspend fun getCharacters(page: Int, name: String?): CharactersDto {
        return charactersPages.getOrElse(page) {
            throw NotFoundException()
        }
    }

    override suspend fun getCharacterDetails(characterId: Int): SingleCharacterDto {
        return allCharacters.find { character ->
            character.id == characterId
        }?.toSingleCharacter() ?: throw Resources.NotFoundException()
    }
}

class NotFoundException : Exception("Not Found")


