package com.example.daggerlessons.lessons16_Hilt_2

import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.lessons15_Hilt.OrderRepository15
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderActivity16: AppCompatActivity() {

    @Inject
    lateinit var repository: OrderRepository15
}