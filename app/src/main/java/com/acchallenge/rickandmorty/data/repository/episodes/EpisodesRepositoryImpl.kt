package com.acchallenge.rickandmorty.data.repository.episodes

import com.acchallenge.rickandmorty.domain.model.episodes.Episode
import com.acchallenge.rickandmorty.domain.repository.ServerEpisodesRepository
import com.acchallenge.rickandmorty.domain.repository.episodes.SingleEpisodeRepository
import com.acchallenge.rickandmorty.data.mappers.toEpisode
import com.acchallenge.rickandmorty.data.utils.BaseRepository
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(private val repository: ServerEpisodesRepository) :
    SingleEpisodeRepository, BaseRepository() {

    override suspend fun getEpisode(episodeId: String): Result<List<Episode>> {

        return when {

            // forced to this because when only one episode is passed the api returns an Episode Entity instead of a list.
            // better implementations are welcomed

            episodeId.length > 2 -> {
                safeApiCall {
                    repository.getEpisode(episodeId).map { singleEpisodeDTO ->
                        singleEpisodeDTO.toEpisode()
                    }
                }
            }
            else -> {
                safeApiCall {

                    listOf(
                        repository.getoneEpisode(episodeId).toEpisode()
                    )
                }
            }
        }
    }
}
