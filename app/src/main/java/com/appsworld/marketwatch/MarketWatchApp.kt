package com.appsworld.marketwatch

import android.app.Application
import com.appsworld.marketwatch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarketWatchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarketWatchApp)
            androidLogger()
            modules(appModule)
        }
    }
}
