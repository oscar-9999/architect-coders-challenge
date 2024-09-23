package com.acchallenge.rickandmorty.data.mappers

import com.acchallenge.rickandmorty.data.api.models.episodes.singleepisode.SingleEpisodeDTO
import com.acchallenge.rickandmorty.data.utils.getPageIntFromUrl
import com.acchallenge.rickandmorty.domain.model.episodes.Episode

fun SingleEpisodeDTO.toEpisode(): Episode {
    return Episode(
        airDate = air_date,
        characters = characters.map { character ->
            getPageIntFromUrl(character) ?: 1
        },
        episode = episode,
        id = id,
        name = name
    )
}