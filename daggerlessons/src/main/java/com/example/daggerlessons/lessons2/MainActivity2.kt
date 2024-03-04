package com.example.daggerlessons.lessons2

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App

class MainActivity2: AppCompatActivity() {

    lateinit var databaseHelper: DatabaseHelper2
    lateinit var networkUtils: NetworkUtils2
    lateinit var mainActivityPresenter: MainActivityPresenter2

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val appComponent = (application as App).appComponent2

        databaseHelper = appComponent.getDatabaseHelper()
        networkUtils = appComponent.getNetworkUtils()
        mainActivityPresenter = appComponent.getMainActivityPresenter()

    }
}