package com.example.daggerlessons.lessons3.intoSet

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class NetworkModuleIntoSet {

    @IntoSet
    @Provides
    fun provideAnalytics(): EventHandler {
        return Analytics()
    }

    @IntoSet
    @Provides
    fun provideLogger(): EventHandler {
        return Logger()
    }
}