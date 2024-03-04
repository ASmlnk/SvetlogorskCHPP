package com.example.daggerlessons.lessons7_subcomponent

import com.example.daggerlessons.lessons2.DatabaseHelper2
import com.example.daggerlessons.lessons2.MainActivityPresenter2
import com.example.daggerlessons.lessons2.NetworkUtils2
import com.example.daggerlessons.lessons2.module.NetworkModule2
import com.example.daggerlessons.lessons2.module.StorageModule2
import dagger.Component
import dagger.Subcomponent

@Component(modules = [StorageModule2::class, NetworkModule2::class])
interface AppComponent7 {
    fun getDatabaseHelper7(): DatabaseHelper2
    fun getNetworkUtils7(): NetworkUtils2
    fun getMainComponent7(): MainComponent7
}

@Subcomponent(modules = [MainModule7::class])
interface MainComponent7 {
    fun getMainActivityPresenter7(): MainActivityPresenter2
}