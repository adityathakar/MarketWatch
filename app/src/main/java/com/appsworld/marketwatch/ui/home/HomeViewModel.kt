package com.appsworld.marketwatch.ui.home

import androidx.lifecycle.ViewModel
import com.appsworld.marketwatch.navigation.Navigator
import com.appsworld.marketwatch.navigation.StockDetail

class HomeViewModel(
    private val navigator: Navigator
) : ViewModel() {

    val stocks: List<String> = listOf("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA")

    fun onStockClicked(symbol: String) {
        navigator.navigateTo(StockDetail(symbol))
    }
}
