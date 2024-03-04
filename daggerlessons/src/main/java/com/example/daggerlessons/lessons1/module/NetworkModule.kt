package com.example.daggerlessons.lessons1.module


import dagger.Module
import dagger.Provides


//Модуль для NetworkUtils:
@Module
class NetworkModule {

    @Provides
    fun provideNetworkUtils(): com.example.daggerlessons.lessons1.NetworkUtils {
        return com.example.daggerlessons.lessons1.NetworkUtils()
    }

}