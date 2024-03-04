package com.example.daggerlessons.lessons3.named.module

import com.example.daggerlessons.lessons3.named.ServerApi
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NetworkModule3 {

    @Named("prod")
    @Provides
    fun provideServerApiProd(): ServerApi {
        return ServerApi("prod.server.com")
    }

    @Named("dev")
    @Provides
    fun provideServerApiDev(): ServerApi {
        return ServerApi("dev.server.com")
    }

}