package com.acchallenge.rickandmorty.domain.usecases

import com.acchallenge.rickandmorty.domain.repository.CharacterDetailsRepository
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
) {

    suspend operator fun invoke(id: Int) = characterDetailsRepository.getCharacterDetails(id)
}
