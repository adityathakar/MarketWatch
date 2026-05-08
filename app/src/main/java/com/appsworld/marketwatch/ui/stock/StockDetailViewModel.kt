package com.appsworld.marketwatch.ui.stock

import androidx.lifecycle.ViewModel
import com.appsworld.marketwatch.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onBack() {
        navigator.goBack()
    }
}