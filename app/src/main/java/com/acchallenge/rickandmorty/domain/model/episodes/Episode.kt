package com.acchallenge.rickandmorty.domain.model.episodes

data class Episode(
    val airDate: String,
    val characters: List<Int>,
    val episode: String,
    val id: Int,
    val name: String,

    )
