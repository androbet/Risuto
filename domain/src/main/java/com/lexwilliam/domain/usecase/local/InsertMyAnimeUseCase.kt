package com.example.risuto.domain.usecase.local

import com.example.risuto.data.local.repository.MyAnimeRepository
import com.lexwilliam.domain.model.MyAnime
import javax.inject.Inject

class InsertMyAnimeUseCase
@Inject constructor(
    private val myAnimeRepository: MyAnimeRepository
) {
    suspend operator fun invoke(myAnime: MyAnime) = myAnimeRepository.insert(myAnime)
}