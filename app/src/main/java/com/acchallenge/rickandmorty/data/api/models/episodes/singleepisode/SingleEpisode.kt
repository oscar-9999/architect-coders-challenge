package com.acchallenge.rickandmorty.data.api.models.episodes.singleepisode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleEpisodeDTO(
    @SerialName("air_date")
    val air_date: String,
    @SerialName("characters")
    val characters: List<String>,
    @SerialName("created")
    val created: String,
    @SerialName("episode")
    val episode: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)