package com.example.svetlogorskchpp

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
    }
}