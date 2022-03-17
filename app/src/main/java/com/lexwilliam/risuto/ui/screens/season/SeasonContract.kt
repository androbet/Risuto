package com.lexwilliam.risuto.ui.screens.season

import com.lexwilliam.risuto.base.ViewEvent
import com.lexwilliam.risuto.base.ViewSideEffect
import com.lexwilliam.risuto.base.ViewState
import com.lexwilliam.risuto.model.AnimePresentation
import com.lexwilliam.risuto.model.SeasonListPresentation

class SeasonContract {
    sealed class Event : ViewEvent {
        data class SetSeason(val season: String, val year: Int): Event()
        data class RefreshList(val season: String, val year: Int): Event()
    }

    data class State(
        val season: String,
        val year: Int,
        val seasonList: SeasonListPresentation,
        val seasonAnime: List<AnimePresentation.Data>,
        val isRefreshing: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {}
}