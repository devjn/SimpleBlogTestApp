package com.github.devjn.simpleblogclient

import android.app.Application
import android.content.Context


class App : Application() {

    companion object {
        const val TAG = "MoviesSample"

        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}
