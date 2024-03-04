package com.example.daggerlessons.lessons15_Hilt

import com.example.daggerlessons.lessons2.DatabaseHelper2
import javax.inject.Inject

class App15: HiltApp15() {

    @Inject
    lateinit var databaseHelper: DatabaseHelper15
    override fun onCreate() {
        super.onCreate()
    }
}