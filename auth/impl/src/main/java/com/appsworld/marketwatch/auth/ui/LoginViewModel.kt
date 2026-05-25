package com.appsworld.marketwatch.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsworld.marketwatch.auth.domain.TokenExchanger
import com.appsworld.marketwatch.core.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(
    private val navigator: Navigator,
    private val tokenExchanger: TokenExchanger,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onRequestTokenReceived(requestToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            runCatching { tokenExchanger.exchangeToken(requestToken) }
                .onSuccess { navigator.goBack() }
                .onFailure { _uiState.value = LoginUiState.Error(it.message ?: "Unknown error") }
        }
    }

    fun onLoginError(reason: String = "Login was not completed. Please try again.") {
        _uiState.value = LoginUiState.Error(reason)
    }

    fun onErrorDismissed() {
        _uiState.value = LoginUiState.Idle
    }
}
