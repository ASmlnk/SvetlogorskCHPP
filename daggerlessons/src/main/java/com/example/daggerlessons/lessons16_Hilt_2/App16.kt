package com.example.daggerlessons.lessons16_Hilt_2

import android.app.Application
import com.example.daggerlessons.lessons15_Hilt.DatabaseHelper15
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App16: Application() {

    @Inject lateinit var databaseHelper15: DatabaseHelper15

    override fun onCreate() {
        super.onCreate()
    }
}