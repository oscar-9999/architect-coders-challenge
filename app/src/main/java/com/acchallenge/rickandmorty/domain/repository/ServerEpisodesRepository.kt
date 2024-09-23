package com.acchallenge.rickandmorty.domain.repository

import com.acchallenge.rickandmorty.data.api.models.episodes.allepisodes.Episodes
import com.acchallenge.rickandmorty.data.api.models.episodes.singleepisode.SingleEpisodeDTO

interface ServerEpisodesRepository {

    suspend fun getEpisodes(): Episodes

    suspend fun getEpisode(
        episode: String,
    ): List<SingleEpisodeDTO>

    suspend fun getoneEpisode(
        episode: String,
    ): SingleEpisodeDTO
}
