package com.acchallenge.rickandmorty.data.mappers

import com.acchallenge.rickandmorty.data.api.models.characters.ResultDto
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.Location
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.Origin
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.SingleCharacterDto

fun ResultDto.toSingleCharacter(): SingleCharacterDto {

    return SingleCharacterDto(
        created = created,
        episode = episode,
        gender = gender,
        id = id,
        image = image,
        location = Location(
            location.name,
            location.url
        ),
        name = name,
        origin = Origin(
            origin.name,
            origin.url
        ),
        species = species,
        status = status,
        type = type,
        url = url
    )
}
