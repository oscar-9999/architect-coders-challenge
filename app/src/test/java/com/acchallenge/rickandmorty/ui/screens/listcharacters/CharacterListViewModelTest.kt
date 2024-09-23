package com.acchallenge.rickandmorty.ui.screens.listcharacters

import androidx.paging.PagingData
import app.cash.turbine.test
import com.acchallenge.rickandmorty.appTestShared.fakedata.characters
import com.acchallenge.rickandmorty.domain.usecases.GetCharactersUseCase
import com.acchallenge.rickandmorty.ui.testrules.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var charactersUseCase: GetCharactersUseCase

    private lateinit var vm: CharacterListViewModel

    private val pagingData = flow { emit(PagingData.from(characters)) }

    @Test
    fun `Verify invoke is called`() = runTest {
        vm = buildViewModel()
        runCurrent()
        verify(charactersUseCase).invoke()
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm = buildViewModel()

        vm.characterListState.test {
            assertEquals(
                CharacterListViewModel.CharacterListState(
                    isLoading = false, dataList = null, errorMessage = ""
                ), awaitItem()
            )
            assertEquals(
                CharacterListViewModel.CharacterListState(dataList = pagingData), awaitItem()
            )
            cancel()
        }
    }

    private fun buildViewModel(): CharacterListViewModel {
        runBlocking {
            whenever(charactersUseCase()).thenReturn(pagingData)
        }
        return CharacterListViewModel(charactersUseCase)
    }
}