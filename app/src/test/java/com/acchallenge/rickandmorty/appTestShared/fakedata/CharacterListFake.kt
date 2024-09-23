package com.acchallenge.rickandmorty.appTestShared.fakedata

import com.acchallenge.rickandmorty.domain.model.CharacterItem

val characters = listOf(
    CharacterItem(
        id = 1, name = "Rick", imageUrl = "", status = "Alive",
        species = "Human", origin = "Earth", gender = "Male"
    ),
    CharacterItem(
        id = 2, name = "Rick", imageUrl = "", status = "Alive",
        species = "Human", origin = "Earth", gender = "Male"
    )
)