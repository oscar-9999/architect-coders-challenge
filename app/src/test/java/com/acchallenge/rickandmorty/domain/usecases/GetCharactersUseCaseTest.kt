package com.acchallenge.rickandmorty.domain.usecases

import androidx.paging.PagingData
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetCharactersUseCaseTest {

    @Test
    fun `Invoke calls characters repository`(): Unit = runBlocking {
        val characterItems = listOf(
            CharacterItem(
                id = 1, name = "Rick", imageUrl = "", status = "Alive",
                species = "Human", origin = "Earth", gender = "Male"
            )
        )
        val pagingData = flowOf(PagingData.from(characterItems))

        val getCharactersUseCase = GetCharactersUseCase(mock() {
            on {
                runBlocking {
                    getAllCharacters("Rick")
                }
            } doReturn (pagingData)
        })

        val result = getCharactersUseCase.invoke("Rick")
        assertEquals(pagingData, result)
    }
}