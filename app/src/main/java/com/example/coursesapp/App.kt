package com.example.coursesapp

import android.app.Application
import android.util.Log
import com.example.coursesapp.data.di.dataModule
import com.example.coursesapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupCrashHandler()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                dataModule
            )
        }
    }

    private fun setupCrashHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("APP_CRASH", "Uncaught exception in thread: ${thread.name}", throwable)

            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}