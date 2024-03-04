package com.example.daggerlessons.lessons7_subcomponent

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App

class MainActivity7: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val appComponent = (application as App).appComponent7
        val mainComponent = appComponent.getMainComponent7()
        val presenter = mainComponent.getMainActivityPresenter7()
    }
}