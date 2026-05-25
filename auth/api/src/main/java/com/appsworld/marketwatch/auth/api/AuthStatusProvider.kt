package com.appsworld.marketwatch.auth.api

fun interface AuthStatusProvider {
    fun isLoggedIn(): Boolean
}
