package com.example.daggerlessons.lessons15_Hilt

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class HiltUserActivity15: AppCompatActivity() {

    lateinit var activityComponent: HiltActivityComponent15

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        activityComponent = (application as App15).appComponent15.getActivityComponent()
        activityComponent.injectUserActivity(this as UserActivity15)
    }
}