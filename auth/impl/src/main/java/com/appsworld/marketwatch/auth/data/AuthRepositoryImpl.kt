package com.appsworld.marketwatch.auth.data

import com.appsworld.marketwatch.auth.api.AccessTokenProvider
import com.appsworld.marketwatch.auth.api.AuthStatusProvider
import com.appsworld.marketwatch.auth.domain.TokenExchanger
import com.appsworld.marketwatch.core.infra.SecurePrefsFactory

private const val KEY_ACCESS_TOKEN = "access_token"
private const val PREFS_NAME = "kite_secure_prefs"

class AuthRepositoryImpl(
    private val kiteAuthService: KiteAuthService,
    prefsFactory: SecurePrefsFactory,
) : AuthStatusProvider, AccessTokenProvider, TokenExchanger {

    private val prefs = prefsFactory.get(PREFS_NAME)

    override fun isLoggedIn(): Boolean =
        prefs.getString(KEY_ACCESS_TOKEN, null) != null

    override fun getAccessToken(): String? =
        prefs.getString(KEY_ACCESS_TOKEN, null)

    override suspend fun exchangeToken(requestToken: String): String {
        val response = kiteAuthService.fetchToken(requestToken)
        prefs.edit().putString(KEY_ACCESS_TOKEN, response.accessToken).apply()
        return response.accessToken
    }
}
