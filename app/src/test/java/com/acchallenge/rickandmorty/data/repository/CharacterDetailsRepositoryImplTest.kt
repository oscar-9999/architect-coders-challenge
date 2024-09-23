package com.acchallenge.rickandmorty.data.repository

import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.Location
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.Origin
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.SingleCharacterDto
import com.acchallenge.rickandmorty.domain.repository.ServerCharactersRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharacterDetailsRepositoryImplTest {

    private lateinit var repository: CharacterDetailsRepositoryImpl
    private lateinit var serverCharactersRepository: ServerCharactersRepository
    private lateinit var singleCharacterDto: SingleCharacterDto

    @Before
    fun setup() {
        serverCharactersRepository = mockk()
        repository = CharacterDetailsRepositoryImpl(serverCharactersRepository)
        singleCharacterDto = SingleCharacterDto(
            "", emptyList(), "", 1, "",
            Location("", ""), "",
            Origin("", ""), "", "", "", ""
        )
    }

    @Test
    fun `test getCharacterDetails returns character details`() = runBlocking {
        // Arrange
        val characterId = 1
        every {
            runBlocking {
                serverCharactersRepository.getCharacterDetails(characterId)
            }
        } returns singleCharacterDto

        // Act
        val result = repository.getCharacterDetails(characterId)

        // Assert
        verify {
            runBlocking {
                serverCharactersRepository.getCharacterDetails(characterId)
            }
        }
        assert(result.isSuccess)
    }

    @Test
    fun `test getCharacterDetails returns error when server repository fails`() = runBlocking {
        // Arrange
        val characterId = 1

        every {
            runBlocking {
                serverCharactersRepository.getCharacterDetails(characterId)
            }
        } throws Exception("Test exception")

        // Act
        val result = repository.getCharacterDetails(characterId)

        // Assert
        verify {
            runBlocking {
                serverCharactersRepository.getCharacterDetails(characterId)
            }
        }
        assert(result.isFailure)
    }
}