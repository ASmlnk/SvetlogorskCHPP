package com.example.svetlogorskchpp

import android.app.Application
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
        FirestoreRepository.initialize(this)
    }
}