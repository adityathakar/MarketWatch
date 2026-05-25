package com.appsworld.marketwatch.auth.di

import com.appsworld.marketwatch.auth.api.AccessTokenProvider
import com.appsworld.marketwatch.auth.api.AuthStatusProvider
import com.appsworld.marketwatch.auth.data.AuthRepositoryImpl
import com.appsworld.marketwatch.auth.data.KiteAuthService
import com.appsworld.marketwatch.auth.domain.TokenExchanger
import com.appsworld.marketwatch.auth.ui.LoginViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.bind
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.viewModel

val authModule = module {

    single<KiteAuthService>()

    single<AuthRepositoryImpl>()
        .bind(AuthStatusProvider::class)
        .bind(AccessTokenProvider::class)
        .bind(TokenExchanger::class)

    viewModel<LoginViewModel>()
}
