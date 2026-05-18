package com.appsworld.marketwatch.di

import com.appsworld.marketwatch.navigation.Navigator
import com.appsworld.marketwatch.ui.home.HomeViewModel
import com.appsworld.marketwatch.ui.stock.StockDetailViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.viewModel

val appModule = module {
    single<Navigator>()
    viewModel<HomeViewModel>()
    viewModel<StockDetailViewModel>()
}
