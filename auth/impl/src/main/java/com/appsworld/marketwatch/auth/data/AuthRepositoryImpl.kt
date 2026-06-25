package com.appsworld.marketwatch.auth.data

import com.appsworld.marketwatch.auth.api.AccessTokenProvider
import com.appsworld.marketwatch.auth.api.AuthStatusProvider
import com.appsworld.marketwatch.auth.domain.TokenExchanger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val kiteAuthService: KiteAuthService,
    private val tokenStore: SecureTokenStore,
) : AuthStatusProvider, AccessTokenProvider, TokenExchanger {

    override fun isLoggedIn(): Flow<Boolean> =
        tokenStore.token.map { it != null }

    override suspend fun getAccessToken(): String? =
        tokenStore.token.first()

    override suspend fun exchangeToken(requestToken: String) {
        val response = kiteAuthService.fetchToken(requestToken)
        tokenStore.saveToken(response.accessToken)
    }
}
