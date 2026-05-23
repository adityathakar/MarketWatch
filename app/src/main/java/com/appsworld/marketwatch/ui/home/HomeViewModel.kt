package com.appsworld.marketwatch.ui.home

import androidx.lifecycle.ViewModel
import com.appsworld.marketwatch.auth.api.LoginRoute
import com.appsworld.marketwatch.core.navigation.Navigator
import com.appsworld.marketwatch.navigation.StockDetail

class HomeViewModel(
    private val navigator: Navigator
) : ViewModel() {

    val stocks: List<String> = listOf("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA")

    fun onStockClicked(symbol: String) {
        navigator.navigateTo(StockDetail(symbol))
    }

    fun onLoginClicked() {
        navigator.navigateTo(LoginRoute)
    }
}
