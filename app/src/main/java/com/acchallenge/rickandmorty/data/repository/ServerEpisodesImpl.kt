package com.acchallenge.rickandmorty.data.repository

import com.acchallenge.rickandmorty.data.ApiService
import com.acchallenge.rickandmorty.data.api.models.episodes.allepisodes.Episodes
import com.acchallenge.rickandmorty.data.api.models.episodes.singleepisode.SingleEpisodeDTO
import com.acchallenge.rickandmorty.domain.repository.ServerEpisodesRepository
import javax.inject.Inject

class ServerEpisodesImpl @Inject constructor(private val apiService: ApiService) : ServerEpisodesRepository {
    override suspend fun getEpisodes(): Episodes {
        return apiService.getEpisodes()
    }

    override suspend fun getEpisode(episode: String): List<SingleEpisodeDTO> {
        return apiService.getEpisode(episode)
    }

    override suspend fun getoneEpisode(episode: String): SingleEpisodeDTO {
        return apiService.getoneEpisode(episode)
    }
}
