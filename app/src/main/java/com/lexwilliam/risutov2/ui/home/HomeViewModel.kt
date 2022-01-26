package com.lexwilliam.risutov2.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.risuto.domain.usecase.remote.GetCurrentSeasonAnimeUseCase
import com.example.risuto.domain.usecase.remote.TopAnimeUseCase
import com.lexwilliam.risutov2.base.BaseViewModel
import com.lexwilliam.risutov2.model.AnimeListPresentation
import com.lexwilliam.risutov2.util.Error
import com.lexwilliam.risutov2.util.ExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val getCurrentSeasonAnimeUseCase: GetCurrentSeasonAnimeUseCase,
    private val topAnimeUseCase: TopAnimeUseCase,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() {

    override val coroutineExceptionHandler= CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        onError(message)
    }

    private var homeJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        homeJob?.cancel()
    }

    private val currentSeasonAnime = MutableStateFlow<List<AnimeListPresentation>>(listOf())
    private val topAiringAnime = MutableStateFlow<List<AnimeListPresentation>>(listOf())
    private val topAnime = MutableStateFlow<List<AnimeListPresentation>>(listOf())
    private val topUpcomingAnime = MutableStateFlow<List<AnimeListPresentation>>(listOf())

    private val _state = MutableStateFlow(HomeViewState(isLoading = false, error = null))
    val state = _state.asStateFlow()

    init {
        homeJob?.cancel()
        homeJob = launchCoroutine {
            refresh()
            combine(
                currentSeasonAnime,
                topAiringAnime,
                topAnime,
                topUpcomingAnime
            ) { currentSeasonAnime, topAiringAnime, topAnime, topUpcoming ->
                HomeViewState(
                    currentSeasonAnime = currentSeasonAnime,
                    topAiringAnime = topAiringAnime,
                    topAnime = topAnime,
                    topUpcomingAnime = topUpcoming,
                    isLoading = false,
                    error = null
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            onCurrentSeasonAnime()
            onTopAiringAnime()
            onTopAnime()
            onTopUpcomingAnime()
        }
    }

    private fun onCurrentSeasonAnime() {
        viewModelScope.launch {
            getCurrentSeasonAnimeUseCase.invoke().collect { results ->
                val animes = results.map { anime -> com.lexwilliam.risutov2.mapper.toPresentation() }
                currentSeasonAnime.value = animes
            }
        }
    }

    private fun onTopAiringAnime() {
        viewModelScope.launch {
            topAnimeUseCase.invoke(1, "airing").collect { results ->
                val animes = results.map { anime -> com.lexwilliam.risutov2.mapper.toPresentation() }
                topAiringAnime.value = animes
            }
        }
    }

    private fun onTopUpcomingAnime() {
        viewModelScope.launch {
            topAnimeUseCase.invoke(1, "upcoming").collect { results ->
                val animes = results.map { anime -> com.lexwilliam.risutov2.mapper.toPresentation() }
                topUpcomingAnime.value = animes
            }
        }
    }

    private fun onTopAnime() {
        viewModelScope.launch {
            topAnimeUseCase.invoke(1, "tv").collect { results ->
                val animes = results.map { anime -> com.lexwilliam.risutov2.mapper.toPresentation() }
                topAnime.value = animes
            }
        }
    }

    private fun onError(message: Int){
        _state.value = _state.value.copy(error = Error(message))
    }
}

data class HomeViewState (
    val currentSeasonAnime: List<AnimeListPresentation> = emptyList(),
    val topAnime: List<AnimeListPresentation> = emptyList(),
    val topAiringAnime: List<AnimeListPresentation> = emptyList(),
    val topUpcomingAnime: List<AnimeListPresentation> = emptyList(),
    val isLoading: Boolean,
    val error: Error?
)