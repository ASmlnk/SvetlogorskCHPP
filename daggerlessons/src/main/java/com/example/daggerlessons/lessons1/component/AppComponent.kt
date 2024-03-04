package com.example.daggerlessons.lessons1.component

import com.example.daggerlessons.lessons1.DatabaseHelper
import com.example.daggerlessons.lessons1.MainActivity1
import com.example.daggerlessons.lessons1.NetworkUtils
import com.example.daggerlessons.lessons1.module.NetworkModule
import com.example.daggerlessons.lessons1.module.StorageModule
import dagger.Component
import dagger.Lazy

@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    //get методы
    fun getDatabaseHelper(): DatabaseHelper
    fun getNetworkUtils(): NetworkUtils
    fun getNetworkUtilsLazy(): Lazy<NetworkUtils>

    //inject методы
    fun injectMainActivity(mainActivity1: MainActivity1)


}