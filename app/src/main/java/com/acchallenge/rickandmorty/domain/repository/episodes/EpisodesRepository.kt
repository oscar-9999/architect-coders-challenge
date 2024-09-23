package com.acchallenge.rickandmorty.domain.repository.episodes

import androidx.paging.PagingData
import com.acchallenge.rickandmorty.domain.model.episodes.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {
    suspend fun getAllEpisodes(): Flow<PagingData<Episode>>
}

interface SingleEpisodeRepository {
    suspend fun getEpisode(episodeId: String): Result<List<Episode>>
}
