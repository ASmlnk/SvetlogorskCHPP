package com.example.daggerlessons.lessons3.lazy

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App
import com.example.daggerlessons.lessons1.NetworkUtils
import dagger.Lazy

class MainActivityLazy: AppCompatActivity() {

    lateinit var networkUtils: Lazy<NetworkUtils>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        networkUtils = (application as App).appComponent.getNetworkUtilsLazy()

        //вызов создание обьекта
        val networkUtils = networkUtils.get()
    }



}