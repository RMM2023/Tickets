package com.portfoliormm.tickets

import android.app.Application
import com.portfoliormm.tickets.di.networkModule
import com.portfoliormm.tickets.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(networkModule, presentationModule)
        }
    }
}
