package com.appsworld.marketwatch.auth.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("public_token") val publicToken: String,
    @SerialName("user_id")      val userId: String,
    @SerialName("user_name")    val userName: String,
)
