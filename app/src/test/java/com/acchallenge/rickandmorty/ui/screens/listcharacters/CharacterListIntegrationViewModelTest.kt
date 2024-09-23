package com.acchallenge.rickandmorty.ui.screens.listcharacters

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.cash.turbine.test
import com.acchallenge.rickandmorty.appTestShared.buildCharactersRepositoryWith
import com.acchallenge.rickandmorty.appTestShared.fakedata.characters
import com.acchallenge.rickandmorty.domain.repository.CharactersRepository
import com.acchallenge.rickandmorty.domain.usecases.GetCharactersUseCase
import com.acchallenge.rickandmorty.ui.testrules.CoroutinesTestRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class CharacterListIntegrationViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var getCharactersUseCase: GetCharactersUseCase

    @Mock
    lateinit var charactersRepository: CharactersRepository

    private lateinit var characterListViewModel: CharacterListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getCharactersUseCase = GetCharactersUseCase(charactersRepository)
    }

    @Test
    fun `data is loaded from server`() = runTest {
        val vm = buildViewModelWith()

        vm.characterListState.test {
            assertEquals(CharacterListViewModel.CharacterListState(), awaitItem())
        }
    }

    @Test
    fun `Verify characterListViewModel is build`() = runTest {
        characterListViewModel = buildViewModel()
        runCurrent()
    }

    private fun buildViewModel(): CharacterListViewModel {
        val pagingData = flowOf(PagingData.from(characters))
        runBlocking {
            whenever(getCharactersUseCase()).thenReturn(pagingData)
        }
        return CharacterListViewModel(getCharactersUseCase)
    }

    private fun buildViewModelWith(
    ): CharacterListViewModel {
        val charactersRepository = buildCharactersRepositoryWith()
        getCharactersUseCase = GetCharactersUseCase(charactersRepository)
        return CharacterListViewModel(getCharactersUseCase)
    }
}