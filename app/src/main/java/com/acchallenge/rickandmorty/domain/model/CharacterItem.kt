package com.acchallenge.rickandmorty.domain.model

data class CharacterItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val gender: String,
    val status: String,
    val species:String,
    val origin:String,
)
