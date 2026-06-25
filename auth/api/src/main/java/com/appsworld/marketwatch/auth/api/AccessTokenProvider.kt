package com.appsworld.marketwatch.auth.api

fun interface AccessTokenProvider {
    suspend fun getAccessToken(): String?
}
