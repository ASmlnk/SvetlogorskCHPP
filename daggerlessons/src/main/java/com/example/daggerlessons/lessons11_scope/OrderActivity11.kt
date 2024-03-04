package com.example.daggerlessons.lessons11_scope

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App

class OrderActivity11: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val orderComponent = (application as App).appComponent11.getOrderComponent11()
        val orderRepository = orderComponent.getOrderRepository()

    }
}