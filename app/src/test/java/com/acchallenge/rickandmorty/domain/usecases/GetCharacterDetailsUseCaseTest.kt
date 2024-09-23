package com.acchallenge.rickandmorty.domain.usecases

import com.acchallenge.rickandmorty.domain.model.CharacterDetails
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetCharacterDetailsUseCaseTest {

    @Test
    fun `Invoke calls character details repository is success`(): Unit = runBlocking {
        val characterDetails = CharacterDetails(
            emptyList(), "", 1, "", "",
            "", "", "", "", ""
        )

        val getCharacterDetailUseCase = GetCharacterDetailsUseCase(mock() {
            on {
                runBlocking {
                    getCharacterDetails(1)
                }
            } doReturn Result.success(characterDetails)
        })

        val result = getCharacterDetailUseCase.invoke(1)
        assert(result.isSuccess)
    }

    @Test
    fun `Invoke calls character details repository is error`(): Unit = runBlocking {
        val getCharacterDetailUseCase = GetCharacterDetailsUseCase(mock() {
            on {
                runBlocking {
                    getCharacterDetails(1)
                }
            } doReturn Result.failure(Throwable())
        })

        val result = getCharacterDetailUseCase.invoke(1)
        assert(result.isFailure)
    }
}