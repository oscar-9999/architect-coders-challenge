package com.acchallenge.rickandmorty.domain.usecases

import androidx.paging.PagingData
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import com.acchallenge.rickandmorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharactersRepository,
) {

    suspend operator fun invoke(name: String? = null): Flow<PagingData<CharacterItem>> {
        return characterRepository.getAllCharacters(name)
    }
}
