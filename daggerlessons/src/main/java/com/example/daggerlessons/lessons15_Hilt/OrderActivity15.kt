package com.example.daggerlessons.lessons15_Hilt

import android.os.Bundle
import android.os.PersistableBundle
import javax.inject.Inject

class OrderActivity15: HiltOrderActivity15() {

    @Inject
    lateinit var repository15: OrderRepository15

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}