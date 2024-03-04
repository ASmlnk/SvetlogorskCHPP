package com.example.daggerlessons.lessons4_inject

import com.example.daggerlessons.lessons1.NetworkUtils
import dagger.Module
import dagger.Provides

@Module
class Module4 {
    @Provides
    fun provideNetworkUtils(): NetworkUtils4 {
        return NetworkUtils4()
    }
}