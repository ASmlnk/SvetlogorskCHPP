package com.example.daggerlessons.lessons15_Hilt

import android.os.Bundle
import android.os.PersistableBundle
import javax.inject.Inject

class UserActivity15: HiltUserActivity15() {

    @Inject
    lateinit var repository15: UserRepository15

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

}