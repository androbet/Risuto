package com.example.risuto.presentation.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.chun2maru.risutomvvm.presentation.mapper.toPresentation
import com.example.risuto.data.local.dao.Results
import com.example.risuto.domain.usecase.local.InsertAnimeHistoryUseCase
import com.example.risuto.domain.usecase.remote.GetAnimeUseCase
import com.example.risuto.domain.usecase.remote.GetCharacterStaffUseCase
import com.example.risuto.presentation.base.BaseViewModel
import com.example.risuto.presentation.mapper.toDomain
import com.example.risuto.presentation.model.AnimePresentation
import com.example.risuto.presentation.model.CharacterStaffPresentation
import com.example.risuto.presentation.util.ExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel
@Inject constructor(
    private val getAnimeUseCase: GetAnimeUseCase,
    private val getCharacterStaffUseCase: GetCharacterStaffUseCase,
    private val insertAnimeHistoryUseCase: InsertAnimeHistoryUseCase,
    savedState: SavedStateHandle
): BaseViewModel() {

    override val coroutineExceptionHandler= CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
    }

    private var detailJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        detailJob?.cancel()
    }

    private val malIdFromArgs = savedState.get<Int>("mal_id")

    private val animeDetail = MutableStateFlow(AnimePresentation())
    private val animeStaff = MutableStateFlow(CharacterStaffPresentation())
    private val _state = MutableStateFlow(AnimeViewState())
    val state = _state.asStateFlow()

    init {
        detailJob?.cancel()
        detailJob = launchCoroutine {
            malIdFromArgs?.let { id ->
                if (id > 0) {
                    getAnimeUseCase.invoke(id).collect { results ->
                        val animes = results.toPresentation()
                        animeDetail.value = animes
                        insertAnimeHistoryUseCase.invoke(animes.toDomain()).collect { result ->
                            if(result == Results.SUCCESS) {
                                Log.d("TAG", "Saving Success")
                            } else {
                                Log.d("TAG", "Saving Failed")
                            }
                        }
                    }
                    getCharacterStaffUseCase.invoke(id).collect { results ->
                        val staffs = results.toPresentation()
                        animeStaff.value = staffs
                    }
                    combine(
                        animeDetail,
                        animeStaff
                    ) { animeDetail, animeStaff ->
                        AnimeViewState(
                            animeDetail = animeDetail,
                            animeStaff = animeStaff,
                            onLoading = false
                        )
                    }.catch {
                        throw it
                    }.collect {
                        _state.value = it
                    }
                }
            }
        }
    }
}

data class AnimeViewState(
    val animeDetail: AnimePresentation = AnimePresentation(),
    val animeStaff: CharacterStaffPresentation = CharacterStaffPresentation(),
    val onLoading: Boolean = true
)