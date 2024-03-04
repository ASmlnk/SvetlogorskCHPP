package com.example.daggerlessons.lessons15_Hilt

import android.app.Application
import com.example.daggerlessons.App

open class HiltApp15: Application() {

    val appComponent15 = DaggerHiltAppCompat15.create()
    override fun onCreate() {
        super.onCreate()
        appComponent15.injectApp(this as App)
    }
}