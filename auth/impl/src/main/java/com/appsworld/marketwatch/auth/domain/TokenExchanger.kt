package com.appsworld.marketwatch.auth.domain

fun interface TokenExchanger {
    suspend fun exchangeToken(requestToken: String): String
}
