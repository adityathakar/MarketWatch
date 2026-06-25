package com.appsworld.marketwatch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsworld.marketwatch.auth.api.AuthStatusProvider
import com.appsworld.marketwatch.auth.api.LoginRoute
import com.appsworld.marketwatch.core.navigation.Navigator
import com.appsworld.marketwatch.core.state.AsyncState
import com.appsworld.marketwatch.navigation.StockDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val navigator: Navigator,
    private val authStatusProvider: AuthStatusProvider,
) : ViewModel() {

    val stocks: List<String> = listOf("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA")

    private val _uiState = MutableStateFlow(HomeUiState(isLoggedIn = AsyncState.Loading))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authStatusProvider.isLoggedIn()
                .map<Boolean, AsyncState<Boolean>> { AsyncState.Success(it) }
                .catch { emit(AsyncState.Failure(it)) }
                .collect { state ->
                    _uiState.update { it.copy(isLoggedIn = state) }
                }
        }
    }

    fun onStockClicked(symbol: String) {
        navigator.navigateTo(StockDetail(symbol))
    }

    fun onLoginClicked() {
        navigator.navigateTo(LoginRoute)
    }
}
