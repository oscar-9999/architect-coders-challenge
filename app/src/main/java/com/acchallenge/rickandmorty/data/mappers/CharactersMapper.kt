package com.acchallenge.rickandmorty.data.mappers

import com.acchallenge.rickandmorty.data.api.models.characters.ResultDto
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.SingleCharacterDto
import com.acchallenge.rickandmorty.data.utils.getEpisodeIntFromUrl
import com.acchallenge.rickandmorty.domain.model.CharacterDetails
import com.acchallenge.rickandmorty.domain.model.CharacterItem

fun ResultDto.toCharacter(): CharacterItem {

    return CharacterItem(
        id = id,
        name = name,
        imageUrl = image,
        gender = gender,
        status = status,
        origin = origin.name,
        species = species

    )
}

fun SingleCharacterDto.toCharacter(): CharacterDetails {
    return CharacterDetails(
        id = id,
        name = name,
        image = image,
        gender = gender,
        status = status,
        episode = episode.map { episode ->
          getEpisodeIntFromUrl(episode) ?: 0

        },
        location = location.name,
        origin = origin.name,
        species = species,
        type = type
    )

}
