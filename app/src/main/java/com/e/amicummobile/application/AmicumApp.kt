package com.e.amicummobile.application

import android.app.Application
import com.e.amicummobile.di.application
import com.e.amicummobile.di.db
import com.e.amicummobile.di.mainScreen
import com.e.amicummobile.di.notification
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AmicumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AmicumApp)
            modules(
                listOf(
                    application,
                    db,
                    mainScreen,
                    notification
                )
            )
        }
    }
}