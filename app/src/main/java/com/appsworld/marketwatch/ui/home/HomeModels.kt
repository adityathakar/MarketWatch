package com.appsworld.marketwatch.ui.home

import com.appsworld.marketwatch.core.state.AsyncState

data class HomeUiState(
    val isLoggedIn: AsyncState<Boolean> = AsyncState.Idle,
)
