package com.example.daggerlessons.lessons20_customComponent

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//родительский фрагмент
@AndroidEntryPoint
class RootFragment20: Fragment() {

    @Inject
    lateinit var myComponentManager: MyComponentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            myComponentManager.create()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}