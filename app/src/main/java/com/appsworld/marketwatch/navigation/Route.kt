package com.appsworld.marketwatch.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class StockDetail(val symbol: String) : NavKey