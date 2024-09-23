package com.acchallenge.rickandmorty.data

import com.acchallenge.rickandmorty.data.api.models.characters.CharactersDto
import com.acchallenge.rickandmorty.data.api.models.characters.singleCharacter.SingleCharacterDto
import com.acchallenge.rickandmorty.data.api.models.episodes.allepisodes.Episodes
import com.acchallenge.rickandmorty.data.api.models.episodes.singleepisode.SingleEpisodeDTO
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(CHARACTERSPATH)
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
    ): CharactersDto

    @GET("$CHARACTERSPATH/{character_id}")
    suspend fun getCharacterDetails(
        @Path("character_id") characterId: Int,
    ): SingleCharacterDto

    @GET(EPISODESPATH)
    suspend fun getEpisodes(): Episodes

    @GET("$EPISODEID{episode}")
    suspend fun getEpisode(
        @Path("episode") episode: String,
    ): List<SingleEpisodeDTO>

    @GET("$EPISODEID{episode}")
    suspend fun getoneEpisode(
        @Path("episode") episode: String,
    ): SingleEpisodeDTO
}

const val CHARACTERSPATH = "character"
const val EPISODESPATH = "episode"
const val EPISODEID = "episode/"
