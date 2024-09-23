package com.acchallenge.rickandmorty.data.api.models.episodes.allepisodes

import com.acchallenge.rickandmorty.data.api.models.episodes.singleepisode.SingleEpisodeDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episodes(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val results: List<SingleEpisodeDTO>
)