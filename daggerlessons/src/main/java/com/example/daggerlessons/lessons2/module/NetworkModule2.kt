package com.example.daggerlessons.lessons2.module

import com.example.daggerlessons.lessons2.ConnectionManager2
import com.example.daggerlessons.lessons2.NetworkUtils2
import com.example.daggerlessons.lessons2.Repository2
import dagger.Module
import dagger.Provides

@Module
class NetworkModule2 {
    @Provides
    fun provideNetworkUtils(connectionManager: ConnectionManager2): NetworkUtils2 {
        return NetworkUtils2(connectionManager)
    }

    @Provides
    fun provideConnectionManager(): ConnectionManager2 {
        return ConnectionManager2()
    }
}