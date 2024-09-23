package com.acchallenge.rickandmorty.domain.usecases.episodes

import com.acchallenge.rickandmorty.domain.model.episodes.Episode
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetEpisodesUseCaseTest {

    @Test
    fun `Invoke calls episodes repository is success`(): Unit = runBlocking {
        val episodes = listOf(
            Episode(
                id = 1, name = "Rick", airDate = "Alive",
                episode = "Human", characters = emptyList()
            )
        )

        val getEpisodesUseCase = GetEpisodesUseCase(mock() {
            on {
                runBlocking {
                    getEpisode("1")
                }
            } doReturn Result.success(episodes)
        })

        val result = getEpisodesUseCase.invoke("1")
        assert(result.isSuccess)
    }

    @Test
    fun `Invoke calls episodes repository is error`(): Unit = runBlocking {
        val getEpisodesUseCase = GetEpisodesUseCase(mock() {
            on {
                runBlocking {
                    getEpisode("1")
                }
            } doReturn Result.failure(Throwable())
        })

        val result = getEpisodesUseCase.invoke("1")
        assert(result.isFailure)
    }
}