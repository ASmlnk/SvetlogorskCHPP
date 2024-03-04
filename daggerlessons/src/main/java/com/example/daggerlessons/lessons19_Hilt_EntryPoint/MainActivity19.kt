package com.example.daggerlessons.lessons19_Hilt_EntryPoint

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity19: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val entryPoint = EntryPoints.get(this, DatabaseEntryPointActivity::class.java)
        val databaseHelper = entryPoint.getDatabaseHelper()
    }
}