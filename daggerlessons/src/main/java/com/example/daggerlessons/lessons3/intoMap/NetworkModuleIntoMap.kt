package com.example.daggerlessons.lessons3.intoMap

import com.example.daggerlessons.lessons3.intoSet.Analytics
import com.example.daggerlessons.lessons3.intoSet.EventHandler
import com.example.daggerlessons.lessons3.intoSet.Logger
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class NetworkModuleIntoMap {
    @IntoMap
    @StringKey("analytic")
    @Provides
    fun provideAnalytics(): EventHandler {
        return Analytics()
    }

    @IntoMap
    @StringKey("logger")
    @Provides
    fun provideLogger(): EventHandler {
        return Logger()
    }

    @MapKey
    @Retention(AnnotationRetention.RUNTIME)
    annotation class EventHandlerKey(val value:EventHandlerType)

    @IntoMap
    @EventHandlerKey(EventHandlerType.ANALYTICS)
    @Provides
    fun provideAnalytics2(): EventHandler {
        return Analytics()
    }

    @IntoMap
    @EventHandlerKey(EventHandlerType.LOGGER)
    @Provides
    fun provideLogger2(): EventHandler {
        return Logger()
    }
}