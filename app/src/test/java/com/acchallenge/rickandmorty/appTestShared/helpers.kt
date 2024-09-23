package com.acchallenge.rickandmorty.appTestShared

import com.acchallenge.rickandmorty.appTestShared.fakedata.FakeCharactersRepository
import com.acchallenge.rickandmorty.data.repository.CharactersRepositoryImpl
import com.acchallenge.rickandmorty.domain.repository.CharactersRepository
import com.acchallenge.rickandmorty.domain.repository.ServerCharactersRepository

fun buildCharactersRepositoryWith(
): CharactersRepository {
    val serverCharactersRepository: ServerCharactersRepository = FakeCharactersRepository()
    return CharactersRepositoryImpl(serverCharactersRepository)
}