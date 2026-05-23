package com.appsworld.marketwatch.ui.stock

import androidx.lifecycle.ViewModel
import com.appsworld.marketwatch.core.navigation.Navigator

class StockDetailViewModel(
    private val navigator: Navigator
) : ViewModel() {

    fun onBack() {
        navigator.goBack()
    }
}
