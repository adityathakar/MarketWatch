package com.appsworld.marketwatch.auth.api

import kotlinx.coroutines.flow.Flow

fun interface AuthStatusProvider {
    fun isLoggedIn(): Flow<Boolean>
}
