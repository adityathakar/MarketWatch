package com.appsworld.marketwatch.core.infra

import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val infraModule = module {
    single<AppHttpClient>()
}
