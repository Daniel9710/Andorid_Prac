package com.example.android

import android.app.Application

class App: Application() {
    companion object {
        lateinit var prefs : MySharedPreferences
        const val mainIp:String = "http://192.168.201.118:8000/"
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }
}