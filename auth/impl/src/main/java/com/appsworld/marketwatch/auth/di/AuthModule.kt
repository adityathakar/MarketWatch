package com.appsworld.marketwatch.auth.di

import com.appsworld.marketwatch.auth.ui.LoginViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val authModule = module {
    viewModel<LoginViewModel>()
}
