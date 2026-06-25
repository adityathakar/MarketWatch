package com.appsworld.marketwatch.core.state

sealed interface AsyncState<out T> {
    data object Idle : AsyncState<Nothing>
    data object Loading : AsyncState<Nothing>
    data class Success<T>(val value: T) : AsyncState<T>
    data class Failure(val error: Throwable) : AsyncState<Nothing>
}
