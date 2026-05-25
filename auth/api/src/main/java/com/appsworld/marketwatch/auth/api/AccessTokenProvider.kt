package com.appsworld.marketwatch.auth.api

fun interface AccessTokenProvider {
    fun getAccessToken(): String?
}
