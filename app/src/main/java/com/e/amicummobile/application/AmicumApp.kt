package com.e.amicummobile.application

import android.app.Application

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import com.e.amicummobile.BuildConfig
import com.e.amicummobile.di.*

class AmicumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AmicumApp)
            if (BuildConfig.BUILD_TYPE == "debug") {
                modules(
                    listOf(
                        application,
                        repositoryTest,
                        db,
                        mainScreen,
                        notification,
                        storeAmicum
                    )
                )
            } else {
                modules(
                    listOf(
                        application,
                        repositoryProd,
                        db,
                        mainScreen,
                        notification,
                        storeAmicum
                    )
                )
            }
        }
    }
}