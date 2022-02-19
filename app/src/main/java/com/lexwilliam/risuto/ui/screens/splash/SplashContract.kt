package com.lexwilliam.risuto.ui.screens.splash

import com.lexwilliam.risuto.base.ViewEvent
import com.lexwilliam.risuto.base.ViewSideEffect
import com.lexwilliam.risuto.base.ViewState

class SplashContract {
    sealed class Event : ViewEvent {
        data class SetupOAuth(val refreshToken: String?, val expiresIn: Long): Event()
    }

    data class State(
        val isUserLoggedIn: Boolean?,
        val refreshToken: String?,
        val expiresIn: Long,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {}
}