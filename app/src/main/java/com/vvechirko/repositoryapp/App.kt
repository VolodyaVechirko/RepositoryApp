package com.vvechirko.repositoryapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class App : Application() {

    companion object {

        lateinit var instance: App

        fun appContext(): Context = instance.applicationContext

        fun isNetworkAvailable(): Boolean {
            val cm = instance.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo?.isConnected ?: false
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}