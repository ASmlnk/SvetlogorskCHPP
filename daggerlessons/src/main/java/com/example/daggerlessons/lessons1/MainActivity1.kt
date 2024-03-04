package com.example.daggerlessons.lessons1

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App
import javax.inject.Inject

class MainActivity1: AppCompatActivity() {
    lateinit var databaseHelper: DatabaseHelper
    lateinit var networkUtils: NetworkUtils

    //для inject метода
    @Inject lateinit var databaseHelperInject: DatabaseHelper
    @Inject lateinit var networkUtilsInject: NetworkUtils

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val appComponent = (application as App).appComponent
        //вызов get методов
        databaseHelper = appComponent.getDatabaseHelper()
        networkUtils = appComponent.getNetworkUtils()

        //вызов ingect методов
        (application as App).appComponent.injectMainActivity(this)

    }
}