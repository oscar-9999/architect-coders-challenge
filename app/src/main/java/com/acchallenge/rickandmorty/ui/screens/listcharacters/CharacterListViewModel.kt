package com.acchallenge.rickandmorty.ui.screens.listcharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import com.acchallenge.rickandmorty.domain.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    private val _characterListState: MutableStateFlow<CharacterListState> = MutableStateFlow(
        CharacterListState()
    )
    private val _requestLocationPermission: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val requestLocationPermission = _requestLocationPermission.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _characterListState.value.isLoading = false
        _characterListState.value = CharacterListState(
            errorMessage = exception.message!!
        )
    }
    val characterListState = _characterListState.asStateFlow()

    init {
        getCharacters()
    }

    fun setRequestLocationPermission(request: Boolean) {
        _requestLocationPermission.value = request
    }

    fun getCharacters() = viewModelScope.launch(coroutineExceptionHandler) {
        val response = charactersUseCase().cachedIn(viewModelScope)
        _characterListState.value = CharacterListState(dataList = response)
    }

    data class CharacterListState(
        var isLoading: Boolean = false,
        val dataList: Flow<PagingData<CharacterItem>>? = null,
        val errorMessage: String = "",
    )
}