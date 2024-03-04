package com.example.daggerlessons.lessons16_Hilt_2

import androidx.fragment.app.Fragment
import com.example.daggerlessons.lessons15_Hilt.OrderRepository15
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment16: Fragment() {

    @Inject
    lateinit var repository: OrderRepository15
}