package com.acchallenge.rickandmorty.ui.screens.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acchallenge.rickandmorty.domain.model.CharacterDetails
import com.acchallenge.rickandmorty.domain.model.episodes.Episode
import com.acchallenge.rickandmorty.domain.usecases.GetCharacterDetailsUseCase
import com.acchallenge.rickandmorty.domain.usecases.episodes.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _detailsState = mutableStateOf(CharacterDetailsState())
    val detailsState = _detailsState
    private val characterId = mutableStateOf(0)
    val episodeIds: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())

    @ExperimentalCoroutinesApi
    val episodesList = episodeIds.filter { list ->
        list.isEmpty().not()
    }.flatMapLatest { idList ->
        val id = idList.joinToString()

        flow {
            getEpisodesUseCase(id).fold(
                onSuccess = {
                    emit(
                        CharacterEpisodesState(
                            episodesDataList = it
                        )
                    )
                },
                onFailure = {
                    emit(
                        CharacterEpisodesState(
                            errorMessage = it.localizedMessage ?: "Error while loading episodes"
                        )
                    )
                }
            )
        }
    }

    init {
        val id = savedStateHandle.get<Int>("characterId")
        _detailsState.value = CharacterDetailsState(isLoading = true)
        if (id != null) {
            characterId.value = id
            getCharacterbyId()
        }
    }

    fun getCharacterbyId() = viewModelScope.launch {
        val result = getCharacterDetailsUseCase(characterId.value)

        result.onSuccess { details ->
            _detailsState.value = CharacterDetailsState(data = details)
            episodeIds.value = details.episode
        }
        result.onFailure {
                error ->
            _detailsState.value = CharacterDetailsState(errorMessage = error.message)
        }
    }
}

data class CharacterEpisodesState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val episodesDataList: List<Episode> = emptyList()
)

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val data: CharacterDetails? = null,
    val errorMessage: String? = ""
)
