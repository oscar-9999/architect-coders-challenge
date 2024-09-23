package com.acchallenge.rickandmorty.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.acchallenge.rickandmorty.domain.model.CharacterDetails
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import com.acchallenge.rickandmorty.domain.model.episodes.Episode
import com.acchallenge.rickandmorty.domain.usecases.GetCharacterDetailsUseCase
import com.acchallenge.rickandmorty.domain.usecases.episodes.GetEpisodesUseCase
import com.acchallenge.rickandmorty.ui.testrules.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var charactersDetailUseCase: GetCharacterDetailsUseCase

    @Mock
    lateinit var getEpisodesUseCase: GetEpisodesUseCase

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    private lateinit var vm: CharacterDetailsViewModel

    private lateinit var characters: List<CharacterItem>

    val characterId = 1
    val details = CharacterDetails(
        listOf(1, 2), "", 1, "", "",
        "", "", "", "", ""
    )
    val episodes = listOf(Episode("", emptyList(), "", 1, ""))

    @ExperimentalCoroutinesApi
    @Test
    fun `when viewmodel is initialized with id 1 then details are loaded`() = runTest {
        val vm = buildViewModel()
        runCurrent()
        // Assert
        assertEquals(CharacterDetailsState(isLoading = false, data = details), vm.detailsState.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when viewmodel is initialized with id 1 then epidodeIds are updated`() = runTest {
        // Arrange
        val viewModel = buildViewModel()
        runCurrent()

        viewModel.episodeIds.test {
            assertEquals(
                listOf(1, 2), awaitItem()
            )
        }
    }

    private fun buildViewModel(): CharacterDetailsViewModel {
        runBlocking {
            whenever(charactersDetailUseCase(characterId)).doReturn(Result.success(details))
//            whenever(getEpisodesUseCase("1")).doReturn(Result.success(episodes))
            whenever(savedStateHandle.get<Int>("characterId")).doReturn(1)
        }
        return CharacterDetailsViewModel(charactersDetailUseCase, getEpisodesUseCase, savedStateHandle)
    }
}