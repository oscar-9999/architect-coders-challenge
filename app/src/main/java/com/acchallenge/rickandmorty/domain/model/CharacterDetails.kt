package com.acchallenge.rickandmorty.domain.model

data class CharacterDetails(
    val episode: List<Int>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val origin: String,
    val species: String,
    val status: String,
    val type: String,
)