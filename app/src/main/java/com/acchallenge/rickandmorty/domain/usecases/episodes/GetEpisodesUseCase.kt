package com.acchallenge.rickandmorty.domain.usecases.episodes

import com.acchallenge.rickandmorty.domain.repository.episodes.SingleEpisodeRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val episodeRepository: SingleEpisodeRepository) {

    suspend operator fun invoke(id:String) = episodeRepository.getEpisode(id)
}