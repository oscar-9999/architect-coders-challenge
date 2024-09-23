package com.acchallenge.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.acchallenge.rickandmorty.data.mappers.toCharacter
import com.acchallenge.rickandmorty.data.utils.getPageIntFromUrl
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import com.acchallenge.rickandmorty.domain.repository.CharactersRepository
import com.acchallenge.rickandmorty.domain.repository.ServerCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val repository: ServerCharactersRepository,
) : CharactersRepository {
    override suspend fun getAllCharacters(name: String?): Flow<PagingData<CharacterItem>> {
        return Pager(PagingConfig(pageSize = 20)) {
            CharactersPagingSource(repository = repository, name = name)
        }.flow
    }
}

class CharactersPagingSource(
    private val name: String? = null,
    private val repository: ServerCharactersRepository
) : PagingSource<Int, CharacterItem>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int? {
        return state.anchorPosition?.let { anchorposition ->
            state.closestPageToPosition(anchorposition)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        val pageNumber = params.key ?: 1
        return try {
            val charactersResponse = repository.getCharacters(pageNumber, name)
            val characters = charactersResponse.results.map { characterDto ->
                characterDto.toCharacter()
            }
            var nextPage: Int? = null
            if (charactersResponse.info.next != null) {
                nextPage = getPageIntFromUrl(charactersResponse.info.next)
            }
            LoadResult.Page(
                data = characters,
                nextKey = nextPage,
                prevKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
