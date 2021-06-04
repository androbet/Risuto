package com.example.risuto.data.remote.repository

import com.chun2maru.risutomvvm.data.mapper.toDomain
import com.chun2maru.risutomvvm.data.remote.JikanService
import com.example.risuto.domain.model.Anime
import com.example.risuto.domain.model.CharacterStaff
import com.example.risuto.domain.model.SeasonArchive
import com.example.risuto.domain.repository.IItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val jikanService: JikanService
): IItemRepository {

    override suspend fun getAnime(id: Int): Flow<Anime> = flow {
        val animeResponse = jikanService.getAnimeResult(id)
        val item = animeResponse.toDomain()
        emit(item)

    }

    override suspend fun getCharacterStaff(id: Int): Flow<CharacterStaff> = flow {
        val characterStaffResponse = jikanService.getCharacterStaffResult(id)
        val item = characterStaffResponse.toDomain()
        emit(item)
    }

    override suspend fun getSeasonArchive(): Flow<SeasonArchive> = flow {
        val seasonArchive = jikanService.getSeasonArchiveResult()
        val item = seasonArchive.toDomain()
        emit(item)
    }

}