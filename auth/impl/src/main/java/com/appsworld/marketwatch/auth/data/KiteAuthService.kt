package com.appsworld.marketwatch.auth.data

import com.appsworld.marketwatch.auth.BuildConfig
import com.appsworld.marketwatch.core.infra.AppHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
private data class TokenRequest(@SerialName("request_token") val requestToken: String)

class KiteAuthService(private val httpClient: AppHttpClient) {
    suspend fun fetchToken(requestToken: String): TokenResponse =
        httpClient.client.post("${BuildConfig.KITE_AUTH_WORKER_URL}token") {
            contentType(ContentType.Application.Json)
            headers { append(HttpHeaders.Authorization, "Bearer ${BuildConfig.WORKER_SECRET}") }
            setBody(TokenRequest(requestToken))
        }.body()
}
