package com.example.daggerlessons.lessons2.component

import com.example.daggerlessons.lessons1.module.NetworkModule
import com.example.daggerlessons.lessons2.DatabaseHelper2
import com.example.daggerlessons.lessons2.MainActivityPresenter2
import com.example.daggerlessons.lessons2.NetworkUtils2
import com.example.daggerlessons.lessons2.module.MainModule2
import com.example.daggerlessons.lessons2.module.NetworkModule2
import com.example.daggerlessons.lessons2.module.StorageModule2
import dagger.Component

@Component(modules = [
    NetworkModule2::class,
    StorageModule2::class,
    MainModule2::class])
interface AppComponent2 {
    fun getDatabaseHelper(): DatabaseHelper2
    fun getNetworkUtils(): NetworkUtils2
    fun getMainActivityPresenter(): MainActivityPresenter2


}