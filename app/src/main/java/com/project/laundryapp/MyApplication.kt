@file:Suppress("unused") //This file used for Koin Dependency Injection
package com.project.laundryapp

import android.app.Application
import com.project.laundryapp.core.di.networkModule
import com.project.laundryapp.core.di.repositoryModule
import com.project.laundryapp.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}