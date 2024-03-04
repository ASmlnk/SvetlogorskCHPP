package com.example.daggerlessons.lessons15_Hilt

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class HiltOrderActivity15: AppCompatActivity(), IActivityComponent15 {

    lateinit var activityComponent: HiltActivityComponent15

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        activityComponent = (application as App15).appComponent15.getActivityComponent()
        activityComponent.injectOrderActivity(this as OrderActivity15)
    }

    override fun activityComponent(): HiltActivityComponent15 {
        return (application as App15).appComponent15.getActivityComponent()
    }
}